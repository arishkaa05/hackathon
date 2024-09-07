import { defineStore } from "pinia";
import { updateFavorite } from '../hooks/useFavorite';

export const useFavoriteStore = defineStore("favoriteStore", {
  state: () => ({
    favorite: [],
  }),
  actions: {
    async addProduct(id, imageUrl, title,titleUrl,  price) {
      if (this.favorite.some(item => item.id === id)) {
      this.removeProduct(this.favorite.findIndex(item => item.id === id));
      return;
      }
      this.favorite.push({
        id: id,
        imageUrl: imageUrl,
        title: title,
        titleUrl: titleUrl,
        price: price,
      });
      let arr = this.favorite.map(item => item.id)
      const result = await updateFavorite(arr);
    },
    async  removeProduct(id) {
      this.favorite.splice(id, 1);
      let arr = this.favorite.map(item => item.id)
      const result = await updateFavorite(arr);
    },
    isFavorite(id) {
      return this.favorite.some(item => item.id === id)
    },
  },
});
