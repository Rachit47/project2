import { makeRequest } from "./AxiosTemplate";

export const loginUser = async (credentials) => {
    return makeRequest(
        "POST",
        "/api/auth/login",
        {},
        {},
        {
            email: credentials.email,
            password: credentials.password
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
            firstName: userData.firstName,
            lastName: userData.lastName,
            email: userData.email,
            password: userData.password
        }
    );
};

export const logoutUser = async () => {
    return makeRequest(
        "POST",
        "/api/auth/logout",
        {},
        {},
        {}
    );
};
