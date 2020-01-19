export abstract class GlobalVariables {
  private static hostName = 'http://localhost:8080/';

  public static get host() {
    return this.hostName;
  }

}
