import { $host } from "../api";

export interface Subcategory {
  id: number;
  title: string;
  titleUrl: string;
  productIds: number[];
  quantity: number;
  description: string;
  imageUrl: string;
}

export const getSubcategoryByProductId = async (id: string) => {
  try {
    const response = await $host.get<Subcategory[]>(`/v1/subcategory/findById/${id}`);
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getProductBySubcategoryId = async (id: string) => {
  try {
    const response = await $host.get<Subcategory[]>(`/v1/subcategory/findById/${id}/withProducts`);
    return response.data;
  } catch (e) {
    return e;
  }
};


export const getProductBySubcategorytitleUrl = async (titleUrl: string) => {
  try {
    const response = await $host.get<Subcategory[]>(`/v1/subcategory/findByTitleUrl/${titleUrl}/withProducts`);
    return response.data;
  } catch (e) {
    return e;
  }
};

