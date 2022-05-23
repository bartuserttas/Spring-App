import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";
const API_OTHER_USER = "http://localhost:8080/api/test/users/";

class AuthService {
  login(username, password) {
    return axios
      .post(API_URL + "signin", {
        username,
        password,
      })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username, password, email) {
    return axios.post(API_URL + "signup", {
      username,
      password,
      email,
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }

  getUserById(id) {
    return axios.get(API_OTHER_USER + id).then((response) => {
      localStorage.setItem("otherUser", JSON.stringify(response.data));
      return response.data;
    });
  }

  getOtherUser() {
    return JSON.parse(localStorage.getItem("otherUser"));
  }
}

export default new AuthService();
