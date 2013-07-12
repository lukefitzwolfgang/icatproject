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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class Creator extends Composite {

	private static CreatorUiBinder uiBinder = GWT.create(CreatorUiBinder.class);

	private static final IcatGwtServiceAsync icat = IcatGwtServiceAsync.Util.getInstance();

	interface CreatorUiBinder extends UiBinder<Widget, Creator> {
	}

	final private static Logger logger = Logger.getLogger(Creator.class.getName());

	@UiField
	Button button;

	@UiField
	Grid grid;

	@UiField
	Label msgLabel;

	private List<FieldShared> fields;

	private String sessionId;

	private String beanName;

	public Creator(String sessionId, String beanName, List<FieldShared> fields) {
		initWidget(uiBinder.createAndBindUi(this));
		this.sessionId = sessionId;
		this.fields = fields;
		this.beanName = beanName;

		grid.resize(fields.size(), 3);
		int i = 0;
		for (FieldShared field : fields) {
			Label label = new Label(field.getName());
			label.setTitle(field.getComment());
			grid.setWidget(i, 0, label);
			Widget w = null;
			if (field.getRelType().equals("ATTRIBUTE")) {
				if (field.getType().equals("String")) {
					if (field.getStringLength() <= 100) {
						TextBox tb = new TextBox();
						tb.setSize(field.getStringLength() + "em", "2ex");
						w = tb;
					} else {
						TextArea ta = new TextArea();
						int height = Math.min(10, field.getStringLength() / 100 + 1);
						ta.setSize("100em", height + "ex");
						w = ta;
					}
				} else if (field.getType().equals("Date")) {
					TextBox tb = new TextBox();
					tb.setSize("12em", "2ex");
					w = tb;
				} else if (field.getType().equals("boolean")) {
					CheckBox cb = new CheckBox();
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
					w = tb;
				}
			} else {
				ListBox lb = new ListBox();
				for (KeyShared key : field.getKeys()) {
					Long keyVal = key.getId();
					/* Note that null comes back as String "null" so use "" */
					lb.addItem(key.getDisplay(), keyVal == null ? "" : keyVal.toString());
				}
				w = lb;
			}
			if (field.isNotNullable()) {
				grid.setText(i, 1, "*");
			}
			w.setTitle(field.getComment());
			grid.setWidget(i, 2, w);
			i++;
		}
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Map<String, String> values = new HashMap<String, String>();
		int i = 0;
		for (FieldShared field : fields) {
			String name = field.getName();
			logger.fine("Try " + name);
			Widget widget = grid.getWidget(i, 2);
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
		msgLabel.setText("Processing");
		icat.create(sessionId, beanName, values, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				msgLabel.setText(Ice.processFailure(caught));
				msgLabel.addStyleName(IceResources.INSTANCE.css().red());
			}

			@Override
			public void onSuccess(Void result) {
				msgLabel.removeStyleName(IceResources.INSTANCE.css().red());
				msgLabel.setText("Data added to ICAT");
			}
		});
	}

}
