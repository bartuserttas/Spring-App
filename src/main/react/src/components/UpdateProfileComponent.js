import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";
import AuthService from "../services/AuthService";
import UpdateCredentialsService from "../services/UpdateCredentialsService";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const email = (value) => {
  if (!isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        This is not a valid email address.
      </div>
    );
  }
};

const vusername = (value) => {
  if (value.length < 4 || value.length > 25) {
    return (
      <div className="alert alert-danger" role="alert">
        The password char limit is between 6 - 25.
      </div>
    );
  }
};

const vpassword = (value) => {
  if (value.length < 6 || value.length > 25) {
    return (
      <div className="alert alert-danger" role="alert">
        The password char limit is between 6 - 25.
      </div>
    );
  }
};

export default class UpdateProfileComponent extends Component {
  constructor(props) {
    super(props);
    this.handleUpdate = this.handleUpdate.bind(this);
    this.onChangeUsername = this.onChangeUsername.bind(this);
    this.onChangeEmail = this.onChangeEmail.bind(this);
    this.onChangePassword = this.onChangePassword.bind(this);

    this.state = {
      username: "",
      email: "",
      password: "",
      successful: false,
      message: "",
      currentUser: AuthService.getOtherUser(),
    };
  }

  onChangeUsername(newUsername) {
    this.setState({
      username: newUsername.target.value,
    });
  }

  onChangeEmail(newEmail) {
    this.setState({
      email: newEmail.target.value,
    });
  }

  onChangePassword(newPassword) {
    this.setState({
      password: newPassword.target.value,
    });
  }

  handleUpdate(event) {
    event.preventDefault();

    this.setState({
      message: "",
      successful: false,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      UpdateCredentialsService.updateUser(
        this.state.currentUser.id,
        this.state.username,
        this.state.email,
        this.state.password
      ).then(
        (response) => {
          this.setState({
            message: response.data.message,
            successful: true,
          });
        },
        (error) => {
          const resultMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.setState({
            successful: false,
            message: resultMessage,
          });
        }
      );
    }

    var curUser = AuthService.getCurrentUser();
    if (this.state.currentUser.id === curUser.id) {
      AuthService.logout();
      this.props.history.push("/login");
      window.location.reload();
    } else {
      this.props.history.push("/users");
      window.location.reload();
    }
  }

  render() {
    return (
      <div className="col-md-12">
        <div className="card card-container">
          <Form
            onSubmit={this.handleUpdate}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="username">Username</label>
                  <Input
                    placeholder={this.state.currentUser.username}
                    type="text"
                    className="form-control"
                    name="username"
                    value={this.state.username}
                    onChange={this.onChangeUsername}
                    validations={[required, vusername]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <Input
                    placeholder={this.state.currentUser.email}
                    type="text"
                    className="form-control"
                    name="email"
                    value={this.state.email}
                    onChange={this.onChangeEmail}
                    validations={[required, email]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <Input
                    type="password"
                    className="form-control"
                    name="password"
                    value={this.state.password}
                    onChange={this.onChangePassword}
                    validations={[required, vpassword]}
                  />
                </div>

                <div className="form-group">
                  <button className="btn btn-primary btn-block">Save</button>
                </div>
              </div>
            )}

            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton
              style={{ display: "none" }}
              ref={(c) => {
                this.checkBtn = c;
              }}
            ></CheckButton>
          </Form>
        </div>
      </div>
    );
  }
}
