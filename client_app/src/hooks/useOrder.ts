import { $host } from "../api";

export const getOrdert = async () => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.get(`/v1/order/findOrderByUser`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};

export const createOrder = async (userId: string, products: number[], place: string, comment: string, number) => {
  const token = localStorage.getItem("jwt");
  const info = {
    userId: userId,
    productIds: products,
    address: place,
    comment: comment,
    deliveryPrice: number,
  };
  try {
    const response = await $host.post("/v1/order/create", info, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};
