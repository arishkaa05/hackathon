<template>
  <div class="information-section container mx-auto my-5">
    <div class="liner-container mt-5 mb-6 flex justify-center border-b-2 border-[rgba(119,119,119,.17)]">
      <h1 class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase">
        Категории
      </h1>
    </div>
    <div class="grid grid-cols-12">
      <div class="col-span-12 flex lg:col-span-3 md:col-span-4 sm:col-span-6"  v-for="category in categories" :key="category">
        <div class="transition-all-300 py-5 border bg-white  hover:relative hover:z-[2] hover:shadow-xl w-full h-full">
          <a class="w-full "  @click.stop="$router.push(`/category/${category.titleUrl}`)">
            <div class="flex justify-center">
              <img class="w-9" :src="'/public/images/logo/' + category.imageUrl + '.png'"  />
            </div>
            <h2 class="text-[#02346f] font-semibold	 text-center">{{category.title}}</h2>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
import { onMounted, ref } from "vue";
import { getСategory, Category } from "../hooks/useCategory";
 import { defineProps } from "vue";


 let categories = ref<Category[]>( []);

const calculateDiscount = (price: number, oldPrice: number) => {
  return Math.round(100 - (price / oldPrice) * 100);
};

onMounted(async () => {
  const response =  await getСategory();
  categories.value = { ...response }; 
});
</script>