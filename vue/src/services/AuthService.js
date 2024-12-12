import axios from 'axios';
const userToken = localStorage.getItem('token')
const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API,
  headers: {Authorization: `Bearer ${userToken}`}
});

export default {
  login(user) {
    return http.post("/login", user);
  },

  register(user) {
    return http.post("/register", user);
  },

  getUsernameByUserId(id) {
    return http.get(`/user/${id}/username`);
  }
};
