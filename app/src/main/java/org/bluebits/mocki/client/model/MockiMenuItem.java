/**
 * 
 */
package org.bluebits.mocki.client.model;

/**
 * @author satyajit
 * 
 */
public class MockiMenuItem {
	public int Id;
	public String IconFile;
	public String Name;

	public MockiMenuItem(int id, String iconFile, String name) {
		Id = id;
		IconFile = iconFile;
		Name = name;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getIconFile() {
		return IconFile;
	}

	public void setIconFile(String iconFile) {
		IconFile = iconFile;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
