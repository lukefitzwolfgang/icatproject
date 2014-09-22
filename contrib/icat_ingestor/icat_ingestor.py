import ConfigParser
import sys
import logging

import icat
import icat.entities

__author__ = "Leonardo Sala"
__copyright__ = "Copyright 2014, Paul Scherrer Institute"
__credits__ = [""]
__license__ = "LGPL"
__version__ = "3"
__maintainer__ = "Leonardo Sala"
__email__ = "leonardo.sala@psi.ch"


lower_first = lambda s: s[:1].lower() + s[1:] if s else ''
upper_first = lambda s: s[0].upper() + s[1:] if s else ''

# Disables output from SUDS client module logger
logger_suds = logging.getLogger("suds.client")
logger_suds.propagate = False


class ICATIngestor(object):
    """
    Class to ingest information into the ICAT metadata catalog.
    """

    ### These tables contains many-to-many relationships, and need special treatment
    __reference_tables = ["investigationInstrument", "investigationUser"]

    ### Data-types inside ICAT which describe not-entities
    __not_entities = ["String", "Long", "Date"]

    def __init__(self, loglevel=logging.DEBUG):
        """"""
        ### Logger configuration
        self.logger = logging.getLogger("ICATIngestor")
        logging.basicConfig(format='[%(levelname)s] %(name)s - %(message)s')
        self.logger.setLevel(loglevel)

    def read_icat_config(self, config_fname, section_name="icat"):
        """Read the ICAT config, in order to set up a connection."""
        config = ConfigParser.SafeConfigParser()
        config.optionxform = str
        config.read(config_fname)
        self.options = dict(config.items(section_name))

    def connect(self, ):
        """Connects to an ICAT server. Please do read_icat_config() before."""
        self.client = icat.Client(self.options['url'], )  # **self.options.client_kwargs)
        try:
            credentials = {'username': self.options['username'], 'password': self.options['password']}
            self.client.login(self.options['auth'], credentials)
        except icat.ICATSessionError as e:
            self.logger.error(e)
            self.logger.error("hint: edit %s" % self.options.configFile)
            sys.exit()

    def disconnect(self):
        """Clean-up and disconnect from an ICAT server"""
        self.client.cleanup()
        self.client.logout()

    def check_entry(self, entity_type, key_attrs, ):
        """
        Checks existance of an entry of type entity_type using key_attrs, a dictionary of Entity attributes as {'name': 'your mama'}. It search only using attributes that constitute a Unique Key. Returns the searched object, or None.
        """
        # needed to check Constraints
        test_entity = self.client.new(entity_type)
        query = ""
        # if key_attrs is a string
        if isinstance(key_attrs, str):
            query = "%s[name='%s']" % (upper_first(entity_type), key_attrs)
        else:
            for k, v in key_attrs.iteritems():
                # if not part of the Primary Key, continue
                if k not in test_entity.Constraint:
                    continue
                if query == "":
                    query = "%s[ %s='%s' " % (upper_first(entity_type), k, v)
                else:
                    query += " AND %s='%s' " % (k, v)
            if query != "":
                query += " ]"

        # query sanity check
        if query.strip() == "":
            self.logger.warn("empty query for %s", entity_type)
            return None
        # execute the query
        try:
            o = self.client.assertedSearch(query)
        except:
            o = None
        return o

    def fill_reference_tables(self, icat_attrs, entry=None):
        """
        Insert the mixed reference tables (many-to-many) as InvestigationInstrument and InvestigationUser
        """
        for ref_table in self.__reference_tables:
            self.logger.debug("Filling reference table: %s", ref_table)
            entry = self.client.new(ref_table)
            constrains = entry.Constraint

            # loop over constraints
            for c in constrains:
                # if constraint is not in the icat_attrs dictionary, just continue
                if c not in icat_attrs.keys():
                    continue
                attr = self.check_entry(c, icat_attrs[c])

                if attr is None:
                    raise RuntimeError("Cannot fill " + ref_table + ", as required field " + c + " is not in ICAT. Please check")
                else:
                    attr = attr[0]
                    entry.__setattr__(c, attr)

            # add additional attributes from icat_attrs (besides the references: basically, only for investigationUser)
            if ref_table in icat_attrs.keys():
                for c, attr in icat_attrs[ref_table].iteritems():
                    entry.__setattr__(c, attr)

            # create a new entry
            try:
                entry.create()
                entry.update()
            except icat.exception.ICATObjectExistsError:
                self.logger.info(ref_table + " entity already exists")
            except:
                self.logger.error(sys.exc_info())

    def insert_entry(self, entity_fieldname, icat_attrs, entry=None):
        """
        Insert an ICAT entry, provided a dictionary of attributes icat_attrs.
        """
        ### TODO: substitute contraint_attrs to name
        if entity_fieldname in self.__reference_tables:
            return None

        self.logger.debug("-----------%s", entity_fieldname)
        entity_attrs = icat_attrs[entity_fieldname]
        self.logger.debug("entity_attrs: %s", entity_attrs)
        if entry is None:
            entry = self.check_entry(entity_fieldname, entity_attrs)
            if entry is not None:
                self.logger.info(entity_fieldname + " exists")
                return

        # Getting some data, as entity info, and attrs->type map
        entity_classname = upper_first(entity_fieldname)
        entity_info = self.client.getEntityInfo(entity_classname)
        entity_map = dict([(i.name, i.type) for i in entity_info.fields])
        self.logger.debug("[DEBUG] Entity map %s", entity_map)
        entry = self.client.new(entity_fieldname)
        constrain_attrs = entry.Constraint
        self.logger.debug("Constraints: %s", constrain_attrs)

        # Filling compulsory attributes
        for c_attr in constrain_attrs:
            # Fill attributes that are not Entities
            if entity_map[c_attr] in self.__not_entities:
                if c_attr not in entity_attrs:
                    raise RuntimeError(c_attr + " is missing from configuration file")
                entry.__setattr__(c_attr, entity_attrs[c_attr])
            else:
                # checking if the entity is described somewhere
                if c_attr in entity_attrs:
                    attr = self.check_entry(c_attr, entity_attrs[c_attr])
                elif c_attr in icat_attrs:
                    attr = self.check_entry(c_attr, icat_attrs[c_attr]['name'])
                else:
                    raise RuntimeError(c_attr + " is missing from configuration file")

                if attr is not None:
                    # if the entity exists, put it as an attribute
                    entry.__setattr__(c_attr, attr[0])
                else:
                    # else, create it
                    self.logger.debug(c_attr + " not found")
                    self.insert_entry(c_attr, icat_attrs)

        # Filling all the rest, if available
        for c_attr in entity_map.keys():
            if c_attr in constrain_attrs:
                continue
            # Fill attributes that are not Entities
            if entity_map[c_attr] in self.__not_entities:
                if c_attr in entity_attrs:
                    entry.__setattr__(c_attr, entity_attrs[c_attr])
            else:
                # checking if the entity is described somewhere
                if c_attr in entity_attrs:
                    attr = self.check_entry(c_attr, entity_attrs[c_attr])
                elif c_attr in icat_attrs:
                    attr = self.check_entry(c_attr, icat_attrs[c_attr]['name'])
                else:
                    continue
                #    raise RuntimeError(c_attr + " is missing from configuration file")

                if attr is not None:
                    # if the entity exists, put it as an attribute
                    entry.__setattr__(c_attr, attr[0])
                else:
                    # else, create it
                    self.logger.debug(c_attr + " not found")
                    self.insert_entry(c_attr, icat_attrs)

        try:
            entry.create()
            entry.update()
            self.logger.info("Created Entity " + entity_classname)
            return entry
        except icat.exception.ICATValidationError:
            error_msg = sys.exc_info()[1].message
            search_str = "cannot be null."
            find_pos = error_msg.find(search_str)
            if find_pos != -1:
                missing_attr = lower_first(error_msg[:find_pos].strip().split(" ")[-1])
                missing_ent_classname = entity_map[missing_attr]
                attr = self.check_entry(lower_first(missing_ent_classname),
                                        icat_attrs[lower_first(missing_ent_classname)]['name'])
                if attr is not None:
                    try:
                        entry.__setattr__(missing_attr, attr[0])
                        self.logger.info("Added Entity attr " + missing_ent_classname)
                    except icat.exception.ICATValidationError:
                        self.insert_entry(entity_fieldname, icat_attrs, entry)
                else:
                    self.logger.info("[INFO] Missing " + missing_ent_classname + ", trying to create it")
                    attr_entry = self.insert_entry(lower_first(missing_ent_classname), icat_attrs)
                    entry.__setattr__(missing_attr, attr_entry)
            else:
                raise RuntimeError("[ERROR] " + error_msg + ": do not know what to do, cowardly exiting")

            try:
                entry.create()
                entry.update()
                return entry
            except icat.exception.ICATValidationError:
                self.insert_entry(entity_fieldname, icat_attrs, entry=entry)

        except icat.exception.ICATObjectExistsError:
            self.logger.info(entity_fieldname + " entity already exists")
