package hu.bekesi.zoltan.filterBuilder.client.widgets;

import com.extjs.gxt.ui.client.widget.form.ComboBox;

public class ComboBoxHelper {

	public static Object getComboBoxRealValue(ComboBox<?> comboBox) {
		if (comboBox.getValue() != null && comboBox.getRawValue() != null && comboBox.getRawValue().equals(comboBox.getValue().get(comboBox.getDisplayField()))) {
			return comboBox.getValue();
		} else {
			return comboBox.getRawValue();
		}

	}
}
