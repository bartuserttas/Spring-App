import axios from "axios";

const UPDATE_URL = "http://localhost:8080/api/test/update";

class UpdateCredentialsService {
  updateUser(id, username, email, password) {
    return axios.put(UPDATE_URL + "/" + id, {
      username,
      password,
      email,
    });
  }

  getNewUpdateRequests() {
    return axios.get(UPDATE_URL + "/" + 1).then((response) => {
      localStorage.setItem("updateRequest", JSON.stringify(response.data));

      return response.data;
    });
  }

  getRequestUserId() {
    this.getNewUpdateRequests();
    console.log(JSON.parse(localStorage.getItem("updateRequest")));
    return JSON.parse(localStorage.getItem("updateRequest"));
  }

  getOtherUserCredentials() {
    const requestInfo = this.getRequestUserId();
    axios.get(UPDATE_URL + "/" + requestInfo.user_id).then((response) => {
      localStorage.setItem("otherUser", JSON.stringify(response.data));
      return response.data;
    });

    this.deleteRequest(requestInfo.user_id);

    return JSON.parse(localStorage.getItem("otherUser"));
  }

  createRequest(id) {
    return axios.post(UPDATE_URL + "/" + id, { id });
  }

  deleteRequest(id) {
    return axios.delete(UPDATE_URL + "/" + id);
  }
}

export default new UpdateCredentialsService();
