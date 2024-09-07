<template>
  <div>
    <div class="grid mb-6 grid-cols-12 gap-6">
      <div class="col-span-12 lg:col-span-8">
        <swiper
          :options="swiperOptions"
          :spaceBetween="30"
          :navigation="true"
          :loop="true"
          :initialSlide="0"
          :autoplay="{
            delay: 3000,
          }"
          :slidesPerView="1"
          :modules="modules"
          class="mySwiper"
        >
          <swiper-slide>
            <img class="transition-transform rounded-xl h-full w-full object-cover hover:scale-105" src="/public/images/photo/homeOne.jpeg" />
          </swiper-slide>
          <swiper-slide>
            <img class="transition-transform rounded-xl h-full w-full object-cover hover:scale-105" src="/public/images/photo/homeTwo.jpg" />
          </swiper-slide>
          <swiper-slide>
            <img class="transition-transform rounded-xl h-full w-full object-cover hover:scale-105" src="/public/images/photo/homeThree.jpg" />
          </swiper-slide>
        </swiper>
      </div>
      <div class="col-span-12 lg:col-span-4 overflow-hidden">
        <div class="bg-[url('/public/images/photo/for-home.jpg')] flex items-center h-full bg-bottom	bg-cover" >
         <div class="bg-[#00000071] h-full py-10 rounded-xl px-5 w-full"> 
          <h2 class="text-xl text-white  lg:mt-20 mb-6 font-semibold">Чехол для телефона</h2>
          <button  @click.stop="$router.push(`/category/for-home`)"
              class="btn-effect relative flex btn transition-all-300 bg-[#02346f] border-[#02346f] hover:bg-[#bb8d54] hover:border-[#bb8d54] rounded-lg bg-primary-color py-2 px-8 text-white"
            >
              <span class="font-bold uppercase text-white">За покупками!</span>
            </button></div>
        </div>
      </div>
    </div>

    <InfoSection :info="infoBlock"></InfoSection>
    <Slider v-if="newProduct[0]" :products="newProduct"></Slider>
    <ProductBlock
      v-if="tShitrs[0]"
      :title="'Футболки'"
      :urltitle="'t-shirts'"
      :products="tShitrs"
    ></ProductBlock>
    <ProductBlock
      v-if="tShitrs[0]"
      :title="'Свитшоты'"
      :urltitle="'sweatshirts'"
      :products="sweatshirt"
    ></ProductBlock>
    <Categories></Categories>
  </div>
</template>

<script setup lang="ts">
import InfoSection from "../components/InfoSection.vue";
import Slider from "../components/Slider.vue";
import ProductBlock from "../components/ProductBlock.vue";
import Categories from "../components/Categories.vue";
import { onMounted, ref } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";

import "../style/swiper.css";
import "swiper/css";
import {
  Navigation,
  Pagination,
  Autoplay,
  Mousewheel,
  Keyboard,
} from "swiper/modules";
import "swiper/css/navigation";
import "swiper/css/pagination";
import {
  getProductPagination,
  getNewProduct,
  Product,
} from "../hooks/useProduct";
import { defineProps } from "vue";

let infoBlock = [
  {
    img: "delivery.png",
    title: "Бесплатная Доставка",
    description: "На заказы от 1000₽",
  },
  {
    img: "money.png",
    title: "Деньги Вернуться",
    description: "В течении 30 дней",
  },
  {
    img: "secure.png",
    title: "Защита Оплаты",
    description: "Безопасный платеж",
  },
];

const modules = [Navigation, Pagination, Mousewheel, Keyboard, Autoplay];

let tShitrs = ref<Product[]>([]);
let sweatshirt = ref<Product[]>([]);
let newProduct = ref<Product[]>([]);

const calculateDiscount = (price: number, oldPrice: number) => {
  return Math.round(100 - (price / oldPrice) * 100);
};

onMounted(async () => {
  const response = await getProductPagination(0, "футболка");
  tShitrs.value = { ...response };
  const responseSw = await getProductPagination(0, "свитшот");
  sweatshirt.value = { ...responseSw };
  const responseNew = await getNewProduct();
  newProduct.value = { ...responseNew };
});
</script>