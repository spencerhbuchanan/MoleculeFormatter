package molecules;

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

/**
 * <h1>Coordinate Cell Factory</h1> Creates a cell factory to be used in the
 * Molecule table which can be edited either by a spinner or by direct typing.
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class CoordinateCellFactory extends TableCell<molecules.Atom, Number>
{
	private Spinner<Double> coordinateSpinner;

	public CoordinateCellFactory()
	{
	}

	@Override
	protected void updateItem(Number item, boolean empty)
	{
		super.updateItem(item, empty);

		setText(item == null ?	"" :
							item.toString());

		if(item != null)
		{
			double value = item.doubleValue();
			setTextFill(isSelected() ? Color.WHITE :
								value == 0 ?	Color.BLACK :
											value < 0 ? Color.RED :
													Color.GREEN);
		}
	}

	@Override
	public void startEdit()
	{
		super.startEdit();

		double initialValue = super.getItem().doubleValue();

		if(coordinateSpinner == null)
			createCoordinateSpinner();

		coordinateSpinner.getValueFactory().setValue(initialValue);

		setText(null);
		setGraphic(coordinateSpinner);
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();

		/*
		 * Needs to be done like this otherwise it doesn't work. Mysteriously, lets it
		 * reset to previous value....
		 */
		coordinateSpinner.getValueFactory().setValue(123.456);

		setText(super.getItem().toString());
		setGraphic(null);
	}

	@Override
	public void commitEdit(Number newValue)
	{
		super.commitEdit(newValue);

		setText(super.getItem().toString());
		setGraphic(null);
	}

	private void createCoordinateSpinner()
	{
		DoubleSpinnerValueFactory doubleSpinnerFactory = new DoubleSpinnerValueFactory(-Double.MAX_VALUE, Double.MAX_VALUE);

		doubleSpinnerFactory.amountToStepByProperty().set(0.01);

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

		coordinateSpinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);

		coordinateSpinner.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent t)
			{
				if(t.getCode() == KeyCode.ENTER)
				{
					commitEdit(coordinateSpinner.getValue());
				} else if(t.getCode() == KeyCode.ESCAPE)
				{
					cancelEdit();
				}
			}
		});

		// Calls after escape is pressed, effectively calling cancelEdit twice
		coordinateSpinner.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if(!newValue)
				{
					cancelEdit();
				}
			}
		});
	}

}
