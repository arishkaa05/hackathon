import { $host } from "../api";

export interface User {
  id?:number,
  username: string;
  password?: string;
  email?: string;
  lastname: string;
  role?: string;
  imageUrl: string;
  contactNumber: string,
  passwordConfirmation?: string,
  city?:string;
}

export const getUser = async () => {
  try {
    const response = await $host.get("/user/all");
    return response.data;
  } catch (e) {
    return e;
  }
};

export const logoutUser = async () => {
  try {
    const response = await $host.get("/user/logout");
    return response.data;
  } catch (e) {
    return e;
  }
};

export const createUser = async (newUser: any) => {
  try {
    const response = await $host.post("/v1/auth/register", newUser);
    return response;
  } catch (e) {
    return e;
    throw e;
  }
};

export const updateUser = async (updateUser: any) => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.put("/v1/user/update", updateUser, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};

export const loginUser = async (logAUser: any) => {
   try {
    const response = await $host.post("/v1/auth/login", logAUser);
    return response;
  } catch (e) {
    return e;
    throw e;
  }
}; 