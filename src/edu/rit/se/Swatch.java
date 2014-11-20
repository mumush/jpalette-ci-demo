package edu.rit.se;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>
 * The {@link Swatch} is a repository of colors a graphic designer is interested
 * in working with. The swatch may be persisted to a file and restored from that
 * file. Colors may be added to or removed from the swatch. Also, the swatch may
 * convert all its constituent colors all into the same format for printing.
 * That way, it's easier to compare the colors in a list.
 * </p>
 * 
 * @author Steven Kroh (skk8768)
 *
 */
public class Swatch
{
	private List<Color> colors;
	private String name;

	/**
	 * The file extension of swatch files
	 */
	public static final String EXT = ".txt";

	/**
	 * Create a new Swatch
	 * 
	 * @param name
	 *        the name of the swatch
	 * @param colors
	 *        a list of colors making up the initial swatch
	 */
	public Swatch(String name, List<Color> colors)
	{
		this.name = name;
		this.colors = colors;
	}

	public static enum Model
	{
		RGB, HEX, CYMK;
	}

	/**
	 * Add a color into the swatch. As part of the upcoming feature change,
	 * you will need to ensure a color is not added if an equal color is
	 * present in the swatch (even if those two colors are different
	 * subtypes!).
	 * 
	 * @param c
	 *        The color to add
	 * @return true if the color was added, false otherwise
	 */
	public boolean addColor(Color c)
	{
		/* Implement this */

      if(!contains(c)) {
         
         colors.add(c);
         return true;
      }

		return false;
	}

	/**
	 * Returns true if the swatch contains the provided color
	 * 
	 * @param c
	 *        The color to check for in the swatch
	 * @return true if the swatch contains the provided color
	 */
	public boolean contains(Color c)
	{
		/* Implement this */
		
      boolean bool = false;
      
      for(Color c_match : colors) {
         
         if(c_match == c) {
            bool = true;
         }
      }
      
      
		return bool;
	}

	/**
	 * Remove a color from the swatch. You only need to remove the color if
	 * it is present in the swatch <b>in the same format</b> as the one
	 * provided to this function.
	 * 
	 * @param c
	 *        The color to remove
	 * @return the removed color, if removed - null otherwise
	 */
	public Color removeColor(Color c)
	{
		/* Implement this */
		
      ArrayList<Color> new_colors = new ArrayList<Color>();
      Color removed = Color();
      
      for(Color c_remove : colors) {
      
         if(c_remove != c) {
            
            new_colors.add(c_remove);
         }
         else {
            removed = c_remove;
         }
      }
      
      if (colors == new_colors) {
      
         return null;
      }
      
      colors = new_colors;
      return removed;
	}

	@Override
	public String toString()
	{
		String result = String.format("Swatch name: %s%n", name);

		for (Color c : colors) {
			result += String.format("%s%n", c.toString());
		}

		return result;
	}

	/**
	 * Returns a swatch string representation in a similar manner to
	 * {@link #toString()}. However, all colors are converted to the same
	 * model for printing.
	 * 
	 * @param model
	 *        The model to print each color in
	 * @return the string representation of the swatch
	 */
	public String toStringInModel(Model model)
	{
		String result;
		result = String.format("Swatch name: %s (%s)%n", name, model);

		for (Color c : colors) {
			String emit;
			try {
				Color inModel;

				switch (model) {

				case CYMK:
					inModel = c.toCymk();
					break;
				case HEX:
					inModel = c.toHex();
					break;
				case RGB:
				default:
					inModel = c.toRgb();
					break;
				}

				emit = inModel.toString();
			} catch (ColorException e) {
				emit = String.format("(error,%s)",
					e.getMessage());
			}

			result += String.format("%s%n", emit);
		}

		return result;
	}

	/**
	 * Persists the swatch to a file.
	 * 
	 * @return true if the file saved correctly, false otherwise
	 */
	public boolean save()
	{
		File f = new File(name + EXT);
		BufferedWriter w = null;
		try {
			w = new BufferedWriter(new FileWriter(f));
			w.write(this.toString());
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
