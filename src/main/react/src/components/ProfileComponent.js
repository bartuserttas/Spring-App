import React, { Component } from "react";
import { Link } from "react-router-dom";
import AuthService from "../services/AuthService";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.updateUserInfo = this.updateUserInfo.bind(this);

    this.state = {
      currentUser: AuthService.getCurrentUser(),
    };
  }

  updateUserInfo(id) {
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

  render() {
    const { currentUser } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.username}</strong> Profile
          </h3>
        </header>
        <p>
          <strong>Token:</strong> {currentUser.accessToken.substring(0, 20)} ...{" "}
          {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
        </p>
        <p>
          <strong>Id:</strong> {currentUser.id}
        </p>
        <p>
          <strong>Email:</strong> {currentUser.email}
        </p>
        <strong>Authorities:</strong>
        <ul>
          {currentUser.roles &&
            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
        </ul>
        <div>
          <Link to="/update">
            <button
              onClick={() => this.updateUserInfo(currentUser.id)}
              className="btn btn-success"
            >
              Update User Credentials
            </button>
          </Link>
        </div>
      </div>
    );
  }
}
