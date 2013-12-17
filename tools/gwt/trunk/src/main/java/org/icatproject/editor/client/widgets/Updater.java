package org.icatproject.editor.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.icatproject.editor.client.IcatGwtServiceAsync;
import org.icatproject.editor.client.Ice;
import org.icatproject.editor.client.IceResources;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;
import org.icatproject.editor.shared.UpdateShared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class Updater extends Composite {

	final private static Logger logger = Logger.getLogger(Updater.class.getName());

	private static UpdaterUiBinder uiBinder = GWT.create(UpdaterUiBinder.class);

	private static final IcatGwtServiceAsync icat = IcatGwtServiceAsync.Util.getInstance();

	interface UpdaterUiBinder extends UiBinder<Widget, Updater> {
	}

	private String sessionId;

	private String beanName;

	private List<KeyShared> keys;

	protected List<FieldShared> fields;

	private Long id;

	public Updater(String sessionId, String beanName, List<KeyShared> keys) {
		this.sessionId = sessionId;
		this.keys = keys;
		this.beanName = beanName;
		initWidget(uiBinder.createAndBindUi(this));
		grid.resize(keys.size(), 3);

		int i = 0;
		for (KeyShared key : keys) {
			grid.setText(i, 0, key.getDisplay());
			grid.setWidget(i, 1, new RadioButton("Updater"));
			grid.setText(i, 2, key.getId().toString());
			grid.getCellFormatter().setVisible(i, 2, false);
			i++;
		}
	}

	@UiHandler("grid")
	void onGridClick(ClickEvent e) {
		for (int i = 0; i < grid.getRowCount(); i++) {
			if (((RadioButton) grid.getWidget(i, 1)).getValue()) {
				id = Long.parseLong(grid.getText(i, 2));
				icat.getForUpdate(sessionId, beanName, id, new AsyncCallback<UpdateShared>() {

					@Override
					public void onFailure(Throwable caught) {
						Ice.processFailure(caught);
					}

					@Override
					public void onSuccess(UpdateShared result) {
						Map<String, String> values = result.getValues();
						logger.fine("Values " + values);
						fields = result.getFields();
						entity.resize(fields.size(), 3);
						int i = 0;
						for (FieldShared field : fields) {
							String name = field.getName();
							String value = values.get(name);
							Label label = new Label(name);
							label.setTitle(field.getComment());
							entity.setWidget(i, 0, label);
							Widget w = null;
							if (field.getRelType().equals("ATTRIBUTE")) {
								if (field.getType().equals("String")) {
									if (field.getStringLength() <= 100) {
										TextBox tb = new TextBox();
										tb.setSize(field.getStringLength() + "em", "2ex");
										tb.setText(value);
										w = tb;
									} else {
										TextArea ta = new TextArea();
										int height = Math.min(10, field.getStringLength() / 100 + 1);
										ta.setSize("100em", height + "ex");
										ta.setText(value);
										w = ta;
									}
								} else if (field.getType().equals("Date")) {
									TextBox tb = new TextBox();
									tb.setSize("12em", "2ex");
									tb.setText(value);
									w = tb;
								} else if (field.getType().equals("boolean")) {
									CheckBox cb = new CheckBox();
									cb.setValue("true".equals(value));
									w = cb;
								} else if (field.getPermValues() != null) {
									ListBox lb = new ListBox();
									lb.setVisibleItemCount(1);
									for (String val : field.getPermValues()) {
										lb.addItem(val);
									}
									w = lb;
								} else {
									TextBox tb = new TextBox();
									tb.setText(value);
									w = tb;
								}
							} else {
								Long selKey = value == null ? null : Long.parseLong(value);
								ListBox lb = new ListBox();
								for (KeyShared key : field.getKeys()) {
									Long keyVal = key.getId();
									/* Note that null comes back as String "null" so use "" */
									lb.addItem(key.getDisplay(),
											keyVal == null ? "" : keyVal.toString());
									if (keyVal != null && selKey != null
											&& keyVal.longValue() == selKey.longValue()) {
										lb.setSelectedIndex(lb.getItemCount() - 1);
									}
									if (keyVal == null && selKey == null) {
										lb.setSelectedIndex(lb.getItemCount() - 1);
									}
								}
								w = lb;
							}
							if (field.isNotNullable()) {
								entity.setText(i, 1, "*");
							}
							w.setTitle(field.getComment());
							entity.setWidget(i, 2, w);
							i++;
						}

					}
				});
			}
		}

	}

	@UiHandler("button")
	void onButtonClick(ClickEvent e) {
		Long update = null;
		int j = 0;
		for (KeyShared key : keys) {
			if (((CheckBox) grid.getWidget(j, 1)).getValue()) {
				update = key.getId();
			}
			j++;
		}
		if (update == null) {
			msgLabel.addStyleName(IceResources.INSTANCE.css().red());
			msgLabel.setText("Nothing selected to update");
		} else {
			msgLabel.setText("Processing");
			Map<String, String> values = new HashMap<String, String>();
			int i = 0;
			for (FieldShared field : fields) {
				String name = field.getName();
				Widget widget = entity.getWidget(i, 2);
				String value = null;
				if (widget instanceof TextBoxBase) {
					value = ((TextBoxBase) widget).getValue();
				} else if (widget instanceof CheckBox) {
					value = ((CheckBox) widget).getValue() ? "TRUE" : "FALSE";
				} else if (widget instanceof ListBox) {
					value = ((ListBox) widget).getValue(((ListBox) widget).getSelectedIndex());
				} else {
					msgLabel.setText("Internal error - don't know how to deal with widget of class "
							+ widget.getClass() + " for field " + name);
					msgLabel.addStyleName(IceResources.INSTANCE.css().red());
					return;
				}
				value = value.trim();
				if (field.isNotNullable() && value.isEmpty()) {
					msgLabel.setText(name + " must have a value");
					msgLabel.addStyleName(IceResources.INSTANCE.css().red());
					return;
				}
				if (!value.isEmpty()) {
					values.put(name, value);
				}
				i++;
			}
			icat.update(sessionId, beanName, id, values, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					msgLabel.setText(Ice.processFailure(caught));
					msgLabel.addStyleName(IceResources.INSTANCE.css().red());
				}

				@Override
				public void onSuccess(Void result) {
					msgLabel.removeStyleName(IceResources.INSTANCE.css().red());
					msgLabel.setText("Data updated in ICAT");
				}
			});
		}
	}

	@UiField
	Button button;

	@UiField
	Grid grid;

	@UiField
	Grid entity;

	@UiField
	Label msgLabel;

}
