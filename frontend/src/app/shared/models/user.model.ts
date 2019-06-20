import { UserBase } from "./user-base.model";

export class User extends UserBase {
  constructor(
    username: string,
    firstName: string,
    lastName: string,
    email: string,
    public password: string
  ) {
    super(username, firstName, lastName, email);
  }
}
