<template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Результаты поиска
      </h1>
    </div>

    <div class="grid grid-cols-12 gap-5" v-if="products && products[0]">
      <div
        class="col-span-12 flex md:col-span-6 lg:col-span-4"
        v-for="product in products"
        :key="product.id"
      >
        <CardItem :product="product"></CardItem>
      </div>
    </div>
    <h2 v-if="products && products.length === 0" class="text-3xl text-center my-20  text-[#02346f] font-semibold text-primary-color">По вашему запросу ничего не найдено :(<br>
      <span class="text-gray-500 text-lg">Попробуйте снова</span></h2>
  </div>
</template>

<script setup lang='ts'>
import { onMounted, ref, watch } from "vue";
import { getProductPagination, Product } from "../hooks/useProduct";
import { useUserStore } from "../stores/userStore";
import CardItem from "../components/CardItem.vue";

const userStore = useUserStore();

let products = ref<Product[]>();

watch(
  () => userStore.searchInput,
  async (newValue, oldValue) => {
    if (newValue !== oldValue) {
      setTimeout(async () => {
        const response = await getProductPagination(0, userStore.searchInput);
        products.value = { ...response };
      }, 1000);
    }
  }
);

onMounted(async () => { 
  if (userStore.searchInput.length > 0) {
    const response = await getProductPagination(0, userStore.searchInput);
    products.value = { ...response };

  }
});
</script>