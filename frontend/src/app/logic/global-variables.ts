export abstract class GlobalVariables {
  private static hostName = 'http://localhost:8080/';
  private static tokenAuth: string;

  public static get host() {
    return this.hostName;
  }

  public static get token() {
    return this.tokenAuth;
  }
  public static set token(value: string) {
    this.tokenAuth = value;
  }
}
