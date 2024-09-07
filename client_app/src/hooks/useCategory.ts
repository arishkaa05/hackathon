import { $host } from "../api";

interface SubcategoryDto {
  id: number;
  title: string;
  titleUrl: string;
  productIds: number[];
  quantity: number;
  description: string;
  imageUrl: string;
}

export interface Category {
  id: number;
  title: string;
  titleUrl: string;
  subcategoryDtos: SubcategoryDto[];
  description: string;
  imageUrl: string;
}

export const getСategory = async () => {
  try {
    const response = await $host.get<Сategory[]>("/v1/category/all");
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getСategoryById = async (id: string) => {
  try {
    const response = await $host.get<Сategory[]>(`/v1/category/findById/${id}`);
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getСategoryProductById = async (id: string) => {
  try {
    const response = await $host.get<Сategory[]>(`/v1/category/findById/${id}/products`);
    return response.data;
  } catch (e) {
    return e;
  }
};


export const getСategoryFindByTitle = async (titleUrl: string) => {
  try {
    const response = await $host.get<Сategory[]>(`/v1/category/findByTitle/${titleUrl}/withSubcategories`);
    return response.data;
  } catch (e) {
    return e;
  }
};