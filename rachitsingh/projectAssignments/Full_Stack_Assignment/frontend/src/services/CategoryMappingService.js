import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "https://api.example.com",
  timeout: 10000,
});

export const makeRequest = async (
  method,
  urlTemplate,
  pathVariables = {},
  queryParams = {},
  requestBody = {},
  config = {}
) => {
  let url = urlTemplate;
  for (const key in pathVariables) {
    url = url.replace(`:${key}`, encodeURIComponent(pathVariables[key]));
  }

  try {
    const response = await axiosInstance.request({
      method,
      url,
      params: queryParams,
      data: ["POST", "PUT", "PATCH"].includes(method.toUpperCase())
        ? requestBody
        : undefined,
      ...config,
    });

    return response.data;
  } catch (error) {
    console.error("API error:", error);
    throw error;
  }
};
