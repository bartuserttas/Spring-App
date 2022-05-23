import axios from "axios";

const USERS_REST_API_URL = "http://localhost:8080/api/test/users";

class UserService {
  getUsers() {
    return axios.get(USERS_REST_API_URL);
  }

  deleteUser(id) {
    return axios.delete(USERS_REST_API_URL + "/" + id);
  }
}

export default new UserService();
