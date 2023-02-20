package carsharing.models;

public class Company {
  public int ID;
  public String NAME;

  public Company(final int id, final String name) {
    this.ID = id;
    this.NAME = name;
  }

  public int getID() {
    return ID;
  }

  public void setID(final int ID) {
    this.ID = ID;
  }

  public String getNAME() {
    return NAME;
  }

  public void setNAME(final String NAME) {
    this.NAME = NAME;
  }
}
