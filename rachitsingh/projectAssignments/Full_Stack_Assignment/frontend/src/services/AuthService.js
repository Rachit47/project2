import { makeRequest } from "./AxiosTemplate";

export const loginUser = async (credentials) => {
  return makeRequest(
    "POST",
    "/api/auth/login",
    {},
    {},
    {
      email: credentials.email,
      password: credentials.password,
    }
  );
};

export const registerUser = async (userData) => {
  return makeRequest(
    "POST",
    "/api/auth/register",
    {},
    {},
    {
      fullName: userData.fullName,
      email: userData.email,
      password: userData.password,
      username: userData.username,
      phone: userData.phone,
      gender: userData.gender,
      age: userData.age,
      role: "ROLE_USER",
    }
  );
};

export const logoutUser = async () => {
  return makeRequest("POST", "/api/auth/logout", {}, {}, {});
};
