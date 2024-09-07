<template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Избранное
      </h1>
    </div> 
    <div class="card w-full bg-white shadow-xl">
      <div class="card-body">
        <div class="my-20" v-if="favoriteStore.favorite.length === 0">
           <h3 class="text-center mb-3 text-xl text-[#02346f] font-bold">Вы еще ничего не добавли в избранное</h3>
          <div class="flex justify-center"><a  @click.stop="$router.push('/category')" class="text-3xl cursor-pointer hover:text-[#bb8d54] text-[#02346f] font-bold">За покупками!</a></div>
        </div>
        <div
          class="sm:flex w-full"
          v-for="(product, index) in favoriteStore.favorite"
          :key="product.id"
        >
          <div class="rounded-lg border-2 w-20 p-3 me-4">
            <img
              class="w-16"
              :src="'/public/images/product/' + product.imageUrl"
              :alt="product.title"
            />
          </div>

          <div class="sm:flex items-center w-full justify-between">
            <div class="w-1/2 my-auto me-4">
              <a class="cursor-pointer flex justify-between hover:text-[#bb8d54]" @click.stop="$router.push(`/product/${product.titleUrl}`)"> <h3 class="font-semibold text-xl sm:text-start text-center">
                {{ product.title }}
              </h3></a>
            </div>
            <div class="flex align-center">
              <div class="font-semibold text-xl text-[#02346f]">
                {{  product.price  }}₽
              </div>
              <a
                @mouseover="isHovered = true"
                @mouseleave="isHovered = false"
                @click="favoriteStore.removeProduct(index)"
                class="tooltip ms-3 cursor-pointer my-auto"
                data-tip="Удалить"
              >
                <img
                  class="w-6 h-7"
                  src="/public/images/logo/remuve-gray.png"
                />
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useFavoriteStore } from "../stores/favoriteStore";

const isHovered = ref(false);
const currentPage = ref(1);

const favoriteStore = useFavoriteStore();
</script>

<style scoped>
</style>