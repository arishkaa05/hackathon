<template>
  <div class="h-full">
    <div
      class="liner-container mt-5 flex justify-center border-b-2 border-[rgba(119,119,119,.17)]"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        {{ props.title }}
      </h1>
    </div>
    <div class="grid grid-cols-12 gap-5 my-5">
      <div
        class="group mb-6 relative col-span-3 hidden overflow-hidden rounded-l-lg xl:block"
      >
        <div class=" pt-2 h-full opacity-50">
          <img
            class="transition-transform h-full w-full object-cover hover:scale-110 group-hover:scale-110"
            :src="'/public/images/photo/'+urltitle+'.jpg'"
            :alt="props.title"
          />
        </div>
        <div class="absolute top-0 left-0 flex h-full w-full items-center">
          <div class="z-[2] p-5">
            <h3
              class="text-lg font-bold uppercase text-primary-color drop-shadow-xl"
            >
              {{props.title}}
            </h3>
            <p class="my-5 text-white drop-shadow-md">
             Выбрери <span class="lowercase">{{props.title}}</span> сейчас!
            </p>
            <a
              class="btn-effect btn transition-all-300 bg-[#bb8d54] border-[#bb8d54] hover:bg-[#02346f] hover:border-[#02346f] btn-sm inline-block rounded-lg bg-primary-color py-2 px-3 text-white"
              @click.stop="$router.push(`/category/cloth/${urltitle}`)"
            >
              <span>Смотреть все</span>
            </a>
          </div>
        </div>
      </div>
      <div class="col-span-12 xl:col-span-9">
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
          }"
          :modules="modules"
          class="mySwiper"
        >
          <swiper-slide v-for="product in props.products" :key="product">
            <CardItem :product="product"></CardItem>
          </swiper-slide>
        </swiper>
      </div>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { onMounted } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css/navigation";
import "swiper/css/pagination";
import CardItem from "./CardItem.vue";
import { Product } from "../hooks/useProduct";
import "swiper/css";
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
  title: {
    type: String,
  },
  urltitle: {
    type: String,
  },
});


</script>

<style scoped>
</style>