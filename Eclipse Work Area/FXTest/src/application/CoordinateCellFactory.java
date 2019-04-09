package application;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class CoordinateCellFactory extends TableCell<molecules.Atom, Number>
{
	private Spinner<Double> coordinateSpinner;
	
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
		
		if(coordinateSpinner == null) createCoordinateSpinner();
		
		coordinateSpinner.getValueFactory().setValue(initialValue);
		
		setGraphic(coordinateSpinner);
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();
		//FIXME: Retains edited value in spinner
		setGraphic(null);
	}
	
	private void createCoordinateSpinner()
	{
		DoubleSpinnerValueFactory doubleSpinnerFactory = new DoubleSpinnerValueFactory(-Double.MAX_VALUE, Double.MAX_VALUE);
		
		doubleSpinnerFactory.setConverter(new StringConverter<Double>(){
			private final DecimalFormat df = new DecimalFormat("###.######");

			@Override
			public String toString(Double value)
			{
				// If the specified value is null, return a zero-length String
				if(value == null)
				{
					return "";
				}

				return df.format(value);
			}

			@Override
			public Double fromString(String value)
			{
				try
				{
					// If the specified value is null or zero-length, return null
					if(value == null)
					{
						return null;
					}

					value = value.trim();

					if(value.length() < 1)
					{
						return null;
					}

					// Perform the requested parsing
					return df.parse(value).doubleValue();
				} catch(ParseException ex)
				{
					throw new RuntimeException(ex);
				}
			}
		});
		
		coordinateSpinner = new Spinner<Double>(doubleSpinnerFactory);
		
		coordinateSpinner.setEditable(true);
		
		coordinateSpinner.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent t)
			{
				if(t.getCode() == KeyCode.ENTER)
				{
					commitEdit(coordinateSpinner.getValue());
				} else if(t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
		
		coordinateSpinner.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					cancelEdit();
				}
			}
		});
	}
	
}
