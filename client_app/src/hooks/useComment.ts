import { $host } from "../api";

export interface Сomment {
  id: number;
  productId: number;
  userId: number;
  message: string;
  rating: number | null;
}

export const getСomment = async (id: string) => {
  try {
    const response = await $host.get<Сomment[]>(`/v1/comment/product/${id}/withUserUsername`);
    return response.data;
  } catch (e) {
    return e;
  }
};

export const createСomment = async (productId: string, userId: number, message: string, rating: number) => {
  const token = localStorage.getItem("jwt");
  let info = {
    productId: productId,
    userId: userId,
    message: message,
    rating: rating,
  };
  try {
    const response = await $host.post("/v1/comment/create", info, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response;
  } catch (e) {
    return e;
    throw e;
  }
};
