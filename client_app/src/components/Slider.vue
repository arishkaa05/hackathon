<template>
  <div class="h-full">
    <div
      class="liner-container my-6 flex justify-center border-b-2 border-[rgba(119,119,119,.17)]"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Новинки
      </h1>
    </div>
      <swiper
        :options="swiperOptions"
        :spaceBetween="30"
        :navigation="true"
        :loop="true"
        :initialSlide="0"
        :autoplay="{
          delay: 3000,
        }"
        :breakpoints="{
          576: {
            slidesPerView: 1,
          },
          768: {
            slidesPerView: 2,
          },
          992: {
            slidesPerView: 3,
          },
          1200: {
            slidesPerView: 4,
          },
        }"
        :modules="modules"
        class="mySwiper"
      >
        <swiper-slide v-for="product in props.products" :key="product">
          <CardItem :product="product"></CardItem>
        </swiper-slide>
      </swiper>
  </div>
</template>

<script setup lang='ts'>
import { onMounted } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import CardItem from "./CardItem.vue";
import { Product } from "../hooks/useProduct";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "../style/swiper.css";
import {
  Navigation,
  Pagination,
  Autoplay,
  Mousewheel,
  Keyboard,
} from "swiper/modules";

const modules = [Navigation, Pagination, Mousewheel, Keyboard, Autoplay];

const props = defineProps({
  products: {
    type: Array,
  },
});

</script>

<style scoped>
</style>