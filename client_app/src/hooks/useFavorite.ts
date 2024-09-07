import { $host } from "../api";

export const getFavorite = async (userId: string) => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.get(`/v1/favoriteItem/findByUserId/${userId}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};

export const createFavorite = async () => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.post("/v1/favoriteItem/createFavoriteItem", null, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};
export const updateFavorite = async (productIds: any) => {
  const token = localStorage.getItem("jwt");
   let info = {
    productIds: productIds,
  };
  try {
     const response = await $host.patch("/v1/favoriteItem/updateProducts", info, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};
