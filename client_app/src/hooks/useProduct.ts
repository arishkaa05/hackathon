import { $host } from "../api";

export interface Product {
  id: number;
  title: string;
  oldPrice: number;
  newProduct: boolean;
  hotProduct: boolean;
  price: number;
  description: string;
  quantity: number;
  weight: number;
  titleForUrl: string;
  imageUrl: string;
}

export const getProduct = async () => {
  try {
    const response = await $host.get<Product[]>("/v1/product/all");
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getNewProduct = async () => {
  try {
    const response = await $host.get<Product[]>("/v1/product/new-products");
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getProductById = async (id: string) => {
  try {
    const response = await $host.get<Product>(`/v1/product/findById/${id}`);
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getProductByTitle = async (title: string) => {
  try {
    const response = await $host.get<Product>(`/v1/product/findByTitleForUrl/${title}`);
    return response.data;
  } catch (e) {
    return e;
  }
};
export const getCategoryByProduct = async (productId: string) => {
  try {
    const response = await $host.get(`/v1/product/${productId}/categories`);
    return response.data;
  } catch (e) {
    return e;
  }
};

export const getProductPagination = async (currentPage: number, searchKey: string) => {
  try {
    const response = await $host.get<Product[]>(`/v1/product/all/pagination?searchKey=${searchKey}`);
    return response.data;
  } catch (e) {
    return e;
  }
};
