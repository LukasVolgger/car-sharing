package carsharing.models;

public class Car {
  public int ID;
  public String NAME;
  public int COMPANY_ID;

  public Car(final int ID, final String NAME, final int COMPANY_ID) {
    this.ID = ID;
    this.NAME = NAME;
    this.COMPANY_ID = COMPANY_ID;
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

  public int getCOMPANY_ID() {
    return COMPANY_ID;
  }

  public void setCOMPANY_ID(final int COMPANY_ID) {
    this.COMPANY_ID = COMPANY_ID;
  }
}
