package carsharing.models;

public class Customer {
  public int ID;
  public String NAME;
  public int RENTED_CAR_ID;

  public Customer(final int ID, final String NAME, final int RENTED_CAR_ID) {
    this.ID = ID;
    this.NAME = NAME;
    this.RENTED_CAR_ID = RENTED_CAR_ID;
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

  public int getRENTED_CAR_ID() {
    return RENTED_CAR_ID;
  }

  public void setRENTED_CAR_ID(final int RENTED_CAR_ID) {
    this.RENTED_CAR_ID = RENTED_CAR_ID;
  }
}
