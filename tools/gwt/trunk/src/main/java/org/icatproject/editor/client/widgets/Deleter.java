package org.icatproject.editor.client.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.icatproject.editor.client.IcatGwtServiceAsync;
import org.icatproject.editor.client.Ice;
import org.icatproject.editor.client.IceResources;
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
import com.google.gwt.user.client.ui.Widget;

public class Deleter extends Composite {

	private static DeleterUiBinder uiBinder = GWT.create(DeleterUiBinder.class);

	private static final IcatGwtServiceAsync icat = IcatGwtServiceAsync.Util.getInstance();

	interface DeleterUiBinder extends UiBinder<Widget, Deleter> {
	}

	private String sessionId;
	private String beanName;
	private List<KeyShared> keys;

	public Deleter(String sessionId, String beanName, List<KeyShared> keys) {
		initWidget(uiBinder.createAndBindUi(this));

		this.sessionId = sessionId;
		this.keys = keys;
		this.beanName = beanName;
		display();

	}

	private void display() {
		grid.resize(keys.size(), 2);
		int i = 0;
		for (KeyShared key : keys) {
			grid.setText(i, 0, key.getDisplay());
			grid.setWidget(i, 1, new CheckBox());
			i++;
		}
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		List<Long> deletes = new ArrayList<Long>();
		int i = 0;
		for (KeyShared key : keys) {
			if (((CheckBox) grid.getWidget(i, 1)).getValue()) {
				deletes.add(key.getId());
			}
			i++;
		}
		if (deletes.isEmpty()) {
			msgLabel.addStyleName(IceResources.INSTANCE.css().red());
			msgLabel.setText("Nothing selected to delete");
		} else {
			icat.delete(sessionId, beanName, deletes, new AsyncCallback<List<KeyShared>>() {
				@Override
				public void onFailure(final Throwable caught) {

					icat.getDeleteForm(sessionId, beanName, new AsyncCallback<List<KeyShared>>() {

						@Override
						public void onFailure(Throwable caughtInner) {
							msgLabel.addStyleName(IceResources.INSTANCE.css().red());
							msgLabel.setText(Ice.processFailure(caught)
									+ " Some deletions may have been succesful - but unable to check");
							Deleter.this.keys = Collections.emptyList();
							display();
						}

						@Override
						public void onSuccess(List<KeyShared> keys) {
							msgLabel.addStyleName(IceResources.INSTANCE.css().red());
							msgLabel.setText(Ice.processFailure(caught)
									+ " Some deletions may have been succesful");
							Deleter.this.keys = keys;
							display();
						}
					});
				}

				@Override
				public void onSuccess(List<KeyShared> keys) {
					msgLabel.removeStyleName(IceResources.INSTANCE.css().red());
					msgLabel.setText("Data removed from ICAT");
					Deleter.this.keys = keys;
					display();
				}
			});
		}
	}

	@UiField
	Button button;

	@UiField
	Grid grid;

	@UiField
	Label msgLabel;

}
