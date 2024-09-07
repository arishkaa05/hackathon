import { defineStore } from "pinia";
import { updateBucket } from "../hooks/useBasket";

export const useBasketStore = defineStore("basketStore", {
  state: () => ({
    basket: [],
  }),
  actions: {
    async addProduct(id, imageUrl, title,  titleUrl, price, quantity) {
      if (this.basket.some((item) => item.id === id)) {
        return;
      }
      this.basket.push({
        id: id,
        imageUrl: imageUrl,
        title: title,
        titleUrl: titleUrl,
        price: price,
        quantity: quantity,
      });
      let arr = this.getAllProducts();
      const result = await updateBucket(arr);
    },
    addProductQuantity(id) {
      this.basket[id].quantity += 1;
    },
    getAllProducts() {
      return this.basket.map((item) => item.id);
    },
    getFullPrice() {
      return this.basket.reduce((total, item) => {
        return total + (item.price * item.quantity);
      }, 0);
    },
    clearBasket() {
      this.basket = []
    },
    decreaseProductQuantity(id) {
      if (this.basket[id].quantity > 1) this.basket[id].quantity -= 1;
      else if (this.basket[id].quantity === 1) this.removeProduct(id);
    },
    async removeProduct(id) {
      this.basket.splice(id, 1);
      let arr = this.getAllProducts();
      const result = await updateBucket(arr);
    },
    isFavorite(id) {},
    deleteBasketProduct() {},
  },
});
