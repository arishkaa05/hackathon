import { $host } from "../api";

export const getBucket = async (userId: string) => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.get(`/v1/bucket/findByUserId/${userId}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};

export const createBucket = async () => {
  const token = localStorage.getItem("jwt");
  try {
    const response = await $host.post("/v1/bucket/createBucket", null, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};
export const updateBucket = async (productIds: any) => {
  const token = localStorage.getItem("jwt");
   let info = {
    productIds: productIds,
  };
  try {
     const response = await $host.patch("/v1/bucket/updateProducts", info, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
    return response.data;
  } catch (e) {
    return e;
  }
};
