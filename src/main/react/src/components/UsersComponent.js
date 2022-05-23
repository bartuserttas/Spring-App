import React from "react";
import AuthService from "../services/AuthService";
import UpdateCredentialsService from "../services/UpdateCredentialsService";
import UserService from "../services/UsersService";

class UserInformationPage extends React.Component {
  deleteUser(id) {
    UserService.deleteUser(id);
    window.location.reload();
  }

  updateUser(id) {
    AuthService.getUserById(id).then(
      () => {
        this.props.history.push("/update");
        window.location.reload();
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  }

  constructor(props) {
    super(props);

    this.deleteUser = this.deleteUser.bind(this);
    this.updateUser = this.updateUser.bind(this);

    this.state = {
      users: [],
    };
  }

  componentDidMount() {
    UserService.getUsers().then((response) => {
      this.setState({ users: response.data });
    });
  }

  render() {
    return (
      <div>
        <h1 className="text-center">User Table</h1>
        <table className="table table-striped">
          <thead>
            <tr>
              <td>User Id</td>
              <td>Username</td>
              <td>Password</td>
              <td>Email</td>
              <td></td>
              <td></td>
            </tr>
          </thead>
          <tbody>
            {this.state.users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.password}</td>
                <td>{user.email}</td>
                <td>
                  <button
                    className="btn btn-success"
                    onClick={() => this.updateUser(user.id)}
                  >
                    Update
                  </button>
                </td>
                <td>
                  <button
                    onClick={() => this.deleteUser(user.id)}
                    className="btn btn-danger"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default UserInformationPage;
