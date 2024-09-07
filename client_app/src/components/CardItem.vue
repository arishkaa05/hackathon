<template>
  <div
    class="pt-2 transition-all-300 ease-in-out hover:translate-y-[-0.5rem] hover:z-[2]"
  >
    <div
      class="card-container relative flex h-full flex-col overflow-hidden rounded-lg bg-white mb-6 p-5 shadow-md hover:shadow-xl"
    >
      <div class="absolute top-[10px] right-[10px]">
        
        <div class="p-[2px]">
          <a  v-if="userStore.user.name.length > 0"  :class="{'bg-red-400': favoriteStore.isFavorite(product.id)}"
            class="tippy tippy-left-wishlist btn-wishlist transition-all-300 flex h-9 w-9 cursor-pointer items-center justify-center gap-2 rounded-lg bg-[rgba(0,0,0,.3)] hover:bg-red-400"
            @click="addFavorite()"
          >
            <img
              class="w-6 m-2"
              :src="
                isHoveredLike
                  ? '/public/images/logo/like-gold.png'
                  : '/public/images/logo/like-white.png'
              "
            />
          </a>


          <div class="bg-[rgba(0,0,0,.6)] rounded-lg h-12 w-12" v-if="userStore.user.name.length === 0"><modal-info-like/></div>
        </div>
        <div class="bg-[rgba(0,0,0,.6)] m-[2px] rounded-lg h-12 w-12"  v-if="userStore.user.name.length === 0"><modal-info-bag /> </div>
        <div class="relative p-[2px]" v-if="userStore.user.name.length > 0">
          <a 
            class="tippy tippy-left-card-view btn-open-modal transition-all-300 flex h-9 w-9 cursor-pointer items-center justify-center rounded-lg bg-[rgba(0,0,0,.3)] hover:bg-[#d5a060]"
            @click="addBasket()"
            data-target=".quick-view-modal"
          >
            <img class="w-5" src="/public/images/logo/bag-white.png" />
          </a>
          <span
            v-if="checkObjectAndIndex() >= 0"
            class="badge absolute text-xs top-[4px] right-[4px] h-[15px] min-w-[15px] px-[2px]"
            >{{
              basketStore.basket[checkObjectAndIndex(product.id)].quantity
            }}</span
          >
        </div>
      </div>
      <div class="flex justify-center">
        <img
          class="h-[190px] object-cover"
          :src="'/public/images/product/' + product.imageUrl"
          :alt="product.title"
        />
      </div>
      <div class="my-2 flex justify-between"> 
        <div>
          <span
            class="rounded-md bg-green-300 py-1 px-2 text-xs font-bold uppercase text-white"
            >В наличии
          </span>
        </div>
      </div>
      <div class="my-1 font-semibold h-20">{{ product.title }}</div>
      <div class="my-1 h-16">
        <p class="clamp-2 text-sm text-gray-400">
          {{ truncatedText(product.description) }}
        </p>
      </div>
      <div class="my-2 flex gap-2" >
        <span class="font-bold">Размеры:</span>
        <ul class="flex gap-3">
          <li>S</li>
          <li>M</li>
          <li>L</li>
          <li>XL</li>
        </ul>
      </div>

      <div class="my-1">
        <span class="text-lg font-bold">{{ product.price }}₽</span>
      </div>
      <div class="mt-auto">
        <a
          class="btn-effect transition ease-in-out bg-[#02346f] hover:bg-[#bb8d54] duration-300 flex w-full items-center justify-center rounded-lg bg-primary-color p-2"
          @click="$router.push(`/product/${product.titleForUrl}`)"
        >
          <span class="font-bold uppercase text-white">Посмотреть</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang='ts'>
import { useFavoriteStore } from "../stores/favoriteStore";
import { useBasketStore } from "../stores/basketStore";
import { useUserStore } from "../stores/userStore";
import ModalInfoLike from './ModalInfoLike.vue'
import ModalInfoBag from './ModalInfoBag.vue'
import { computed, ref, reactive, onMounted, watch } from "vue";

const isHoveredBag = ref(false);
const isHoveredLike = ref(false);

const props = defineProps({
  product: {
    type: Object,
  },
}); 

const bannerBgImage = (image: string) => {
  return `background-image: url(${image})`;
};

const favoriteStore = useFavoriteStore();
const basketStore = useBasketStore();
const userStore = useUserStore();

const checkObjectAndIndex = () => {
  const targetId = basketStore.basket.findIndex(
    (obj) => obj.id === props.product.id
  );
  return targetId;
};

const addBasket = async () => {
   if (checkObjectAndIndex() === -1) {
    if (userStore.user.name.length > 0){
      basketStore.addProduct(
        props.product.id,
        props.product.imageUrl,
        props.product.title,
        props.product.titleForUrl,
        props.product.price,
        1
      ); 
    }
    else {
      document.getElementById('info_modal_bag').showModal()
    }
  } else {
     basketStore.addProductQuantity(checkObjectAndIndex());
  }
};


const addFavorite = async () => {
     if (userStore.user.name.length > 0){
      favoriteStore.addProduct(
        props.product.id,
        props.product.imageUrl,
        props.product.title,
         props.product.titleForUrl,
        props.product.price
      ); 
    }
    else {
      document.getElementById('info_modal_like').showModal()
    }
};


const truncatedText = (text: string) => {
  if (text.length <= 80) {
    return text;
  } else {
    let truncatedString = text.substr(0, 80);
    truncatedString = truncatedString.substr(
      0,
      truncatedString.lastIndexOf(" ")
    );
    return truncatedString + " ...";
  }
};
</script>