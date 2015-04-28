Requirements for a usage reporting system for ICAT

# Reporting Requirements #

## DLS ##

  * items to go here

## ILL ##

The most important usage reporting requirements at ILL are those under 'Management information' of ISIS.(perhaps with the possibility to restrict reports on cycles).

Additionally there is a requirement at ILL for 'datafile and parameter' reports like e.g.:
(Wavelength as example, could be every parameter for an instrument)
  1. Report on the average wavelength expansion on Instrument X in the past x days/weeks/months/cycles(Result: list of period - avgVal pairs)
  1. Report on the wavelength distribution for instrument X in the past x days/weeks/months/cycles (result: list of wavelength - #datafile pairs)
  1. How was the total wavelength distribution for instrument X grouped by reactorcycles/years. (result: for each cycles/period a list of wavelength - #datafile pairs)
  1. Compare the average wavelength expansion for 2 or more instruments in the past x days/weeks/months/cycles
  1. Evt. answer to questions like: Are there typical dependencies between the values of the parameters (p1, ..., pN) for an instrument?

Requesters: Scientific responsibles (but also interesting for ILL's instrument responsibles, management, scientists or ICAT users). Similar stats can be imagined on samples (or publications).

## CLF ##

  * items to go here

## ISIS ##

**Management Information**
  1. Report on how many people have logged into ICAT in the past x days/weeks/months
  1. Report on data access (downloads) by investigation, dataset, data age (date), instrument, etc, all filterable by user id
  1. Report on search term use and trending, again filterable by user id


**User Information**
  1. Answer the 'Who has downloaded my data' question. PI (investigation admin) accessible logging of who has accessed that investigation record, who has downloaded it.
  1. Show my previous searches
  1. Show my previous downloads

**General comments**
  1. All reports to be filterable and sortable by user id, institution, date range (days, months or years)