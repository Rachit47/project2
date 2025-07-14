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
  try {
    // Make server request to invalidate token if your backend supports it
    await makeRequest("POST", "/api/auth/logout", {}, {}, {});

    // Clear token and user from localStorage
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return { success: true };
  } catch (error) {
    console.error("Logout error:", error);

    // Even if server logout fails, clear local storage
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return { success: false, error };
  }
};
