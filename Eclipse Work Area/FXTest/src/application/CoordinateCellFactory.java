package application;

import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class CoordinateCellFactory extends TableCell<molecules.Atom, Number>
{
	public CoordinateCellFactory() {}
	
	@Override
	protected void updateItem(Number item, boolean empty)
	{
		super.updateItem(item, empty);
		
		setText(item == null ? "" : item.toString());
		
		if(item != null)
		{
			double value = item.doubleValue();
			setTextFill(isSelected() ? Color.WHITE :
				value == 0 ? Color.BLACK :
				value < 0 ? Color.RED : Color.GREEN);
		}
	}
	
	@Override
	public void startEdit()
	{
		super.startEdit();
		
		double initialValue = super.getItem().doubleValue();
		
		Spinner<Double> coordinateSpinner = new Spinner<Double>(-999.99999999, 999.99999999, initialValue);
		
		//FIXME: Double value is truncated to only two decimal places
		
		setGraphic(coordinateSpinner);
	}
	
	
}
