<template>
  <div>
    <div class="grid pb-10 grid-cols-12 gap-5 rounded-lg bg-white p-2 xs:p-8">
      <div class="col-span-12 max-h-[500px] md:col-span-6">
        <div class="flex justify-center overflow-hidden">
          <a class="hover:scale-125 translate-x-4 skew-y-3">
            <img
              class="object-cover"
              :src="'/public/images/product/' + product.imageUrl"
              :alt="product.name"
            />
          </a>
        </div>
      </div>
      <div class="col-span-12 mb-6 md:col-span-6">
        <h2 class="text-2xl mt-3 font-medium">
          {{ product.title }}
        </h2>
        <div class="my-2 flex justify-between">
          <div class="relative inline-block">
            <span
              v-for="star in [1, 2, 3, 4, 5]"
              :key="star"
              style="flex-shrink: 0"
              class="star inline-block w-6 h-6 mr-2 bg-cover bg-[url('/src/assets/logo/star-outline.png')]"
            >
            </span>
            <div
              :style="`width: ${ratingAll * 20}%;`"
              class="star-rating__colored inline-block absolute w-0 h-full t-[2px] left-0 right-0 overflow-hidden"
            >
              <span
                v-for="star in [1, 2, 3, 4, 5]"
                :key="star"
                style="flex-shrink: 0"
                class="inline-block w-6 h-6 mr-2 bg-cover bg-[url('/src/assets/logo/star-fill.png')]"
              >
              </span>
            </div>
          </div>
          <div class="ml-auto">
            <span
              v-if="product.quantity > 0"
              class="rounded-md bg-green-300 px-2 py-1 text-xs font-bold uppercase text-white"
              >В наличии</span
            >
          </div>
        </div>
        <div class="my-5 flex items-center gap-5">
          <div
            class="flex rounded-lg bg-white px-3 py-2 text-primary-color shadow"
          >
            <span class="text-2xl font-semibold leading-7"
              >{{ product.price }}₽</span
            >
          </div>
          <div class="flex flex-col">
            <span
              class="text-md font-semibold uppercase text-green-400"
              v-if="product.oldPrice > product.price"
            >
              -{{ calculateDiscount(product.price, product.oldPrice) }}%</span
            >
            <span
              class="text-md font-semibold uppercase text-red-400"
              v-if="product.oldPrice < product.price"
            >
              +{{ calculateDiscount(product.price, product.oldPrice) }}%</span
            >
            <span class="prev-price text-sm text-primary-color line-through"
              >{{ product.oldPrice }}₽</span
            >
          </div>
        </div>
        <div class="my-4">
          <p class="clamp-5 break-all">
            {{ product.description }}
          </p>
        </div>
        <div class="flex gap-1">
          <form>
            <div class="block">
              <div class="my-7 flex flex-col gap-1">
                <span class="font-bold">Размеры:</span>
                <ul class="flex flex-wrap gap-3">
                  <li
                    class="relative"
                    v-for="size in ['S', 'M', 'L', 'XL']"
                    :key="size"
                  >
                    <input class="peer sr-only" type="radio" name="size" />
                    <label
                      class="flex h-8 w-10 cursor-pointer items-center justify-center rounded-lg border border-gray-300 bg-white hover:bg-gray-50 focus:outline-none peer-checked:border-transparent peer-checked:ring-2 peer-checked:ring-primary-color"
                      for="size1"
                    >
                      {{ size }}
                    </label>
                  </li>
                </ul>
              </div>
            </div>
            <div class="flex mt-3 flex-wrap justify-start gap-5">
              <div class="quantity inline-flex rounded-lg bg-white shadow">
                <input
                  v-if="checkObjectAndIndex() < 0"
                  class="quantity-value input-number w-12 border-none bg-transparent p-1 text-center text-lg text-gray-400 focus:border-none focus:ring-0"
                  :value="quontity"
                />
                <input
                  v-if="checkObjectAndIndex() >= 0"
                  class="quantity-value input-number w-12 border-none bg-transparent p-1 text-center text-lg text-gray-400 focus:border-none focus:ring-0"
                  :value="
                    basketStore.basket[checkObjectAndIndex(product.id)].quantity
                  "
                />
                <div class="flex w-5 flex-col justify-between">
                  <button
                    class="quantity-btn increment text-primary-color"
                    @click="increment()"
                    type="button"
                  >
                    <img
                      class="w-3 mt-1"
                      src="/public/images/logo/up-blue.png"
                    />
                  </button>
                  <button
                    class="quantity-btn decrement text-primary-color"
                    type="button"
                    @click="decrease()"
                  >
                    <img
                      class="w-3 mb-1"
                      src="/public/images/logo/down-blue.png"
                    />
                  </button>
                </div>
              </div>
              <div class="flex gap-2">
                <button v-if="userStore.user.name.length > 0"
                  @click.prevent="addBasket()"
                  class="btn-effect relative flex btn transition-all-300 bg-[#02346f] border-[#02346f] hover:bg-[#bb8d54] hover:border-[#bb8d54] rounded-lg bg-primary-color py-2 px-8 text-white"
                >
                  <img
                    class="w-6 mr-2"
                    src="/public/images/logo/bag-white.png"
                  />
                  <span class="font-bold uppercase text-white">в корзину</span>
                  <span
                    v-if="checkObjectAndIndex() >= 0"
                    class="badge absolute text-xs top-[4px] right-[4px] h-[15px] min-w-[15px] px-[2px]"
                    >{{
                      basketStore.basket[checkObjectAndIndex(product.id)]
                        .quantity
                    }}</span
                  >
                </button>
                <button data-tip="Необходимо авторизоваться" v-if="userStore.user.name.length === 0"
                @click.stop="$router.push(`/login`)"
                  class="btn-effect tooltip relative flex btn transition-all-300 bg-[#02346f] border-[#02346f] hover:bg-[#bb8d54] hover:border-[#bb8d54] rounded-lg  py-2 px-8 text-white"
                >
                  <img
                    class="w-6 mr-2"
                    src="/public/images/logo/bag-white.png"
                  />
                  <span class="font-bold   text-white">В корзину</span>
                </button>
                <div>
                  <a  v-if="userStore.user.name.length > 0"
                    :class="{
                      'bg-red-400': favoriteStore.isFavorite(product.id),
                    }"
                    class="    transition-all-300 flex cursor-pointer items-center justify-center p-1 rounded-lg bg-[#02346f] hover:bg-[#bb8d54]"
                    @click="addFavorite()"
                  >
                    <img
                      class="w-6 m-2"
                      src="/public/images/logo/like-white.png"
                    />
                  </a>
                  <a data-tip="Необходимо авторизоваться"  v-if="userStore.user.name.length === 0"
                    :class="{
                      'bg-red-400': favoriteStore.isFavorite(product.id),
                    }"
                    class="  tooltip  transition-all-300 flex cursor-pointer items-center justify-center p-1 rounded-lg bg-[#02346f] hover:bg-[#bb8d54]"
                    @click="addFavorite()"
                  >
                    <img
                      class="w-6 m-2"
                      src="/public/images/logo/like-white.png"
                    />
                  </a>
                </div>
              </div>
            </div>
          </form>
        </div>
        <div class="mt-5 border-t border-gray-200 pt-5">
          <div>
            <b>Категории: </b>
            <span
              ><a
                @click.stop="
                  $router.push(`/category/${categoryProduct.titleUrl}`)
                "
                v-for="categoryProduct in category"
                :key="categoryProduct.id"
                class="cursor-pointer hover:text-[#bb8d54] text-zinc-500"
                >{{ categoryProduct.title }}</a
              >
            </span>
          </div>
        </div>
      </div>
    </div>
    <Reviews v-if="product.id > 0" :id="product.id" @rating="handleRating"></Reviews>
  </div>
</template>


<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useFavoriteStore } from "../stores/favoriteStore";
import {
  getProductByTitle,
  getCategoryByProduct,
  Product,
} from "../hooks/useProduct";
import Reviews from "../components/Reviews.vue";
import { useRoute } from "vue-router";
import { useUserStore } from "../stores/userStore";
import { useBasketStore } from "../stores/basketStore";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

const router = useRoute();
const basketStore = useBasketStore();
const favoriteStore = useFavoriteStore();
const userStore = useUserStore();

let ratingAll = ref(0);

let product = ref({
  id: 0,
  title: "",
  newProduct: false,
  hotProduct: false,
  price: 0,
  oldPrice: 0,
  description: "",
  quantity: 0,
  weight: 0,
  titleForUrl: '',
  imageUrl: "",
});
let category = ref({});

let quontity = ref(0);

const decrease = () => {
  if (quontity.value > 0) quontity.value -= 1;
  if (checkObjectAndIndex() >= 0)
    basketStore.basket[checkObjectAndIndex()].quantity -= 1;
  if (quontity.value === 1) basketStore.removeProduct(product.value.id);
};
const increment = () => {
  quontity.value += 1;
  if (checkObjectAndIndex() >= 0)
    basketStore.basket[checkObjectAndIndex()].quantity += 1;
};
const calculateDiscount = (price: number, oldPrice: number) => {
  return Math.round(100 - (price / oldPrice) * 100);
};

const checkObjectAndIndex = () => {
  const targetId = basketStore.basket.findIndex(
    (obj) => obj.id === product.value.id
  );
  return targetId;
};


const handleRating = async (rating: number) => {
  ratingAll.value = rating
};


const addFavorite = async () => {
  if (userStore.user.name.length > 0) {
    favoriteStore.addProduct(
      product.value.id,
      product.value.imageUrl,
      product.value.title,
      product.value.titleForUrl,
      product.value.price
    );
  } else {
    document.getElementById("info_modal_like").showModal();
  }
};

const addBasket = async () => {
  if (checkObjectAndIndex() === -1) {
    if (quontity.value > 0) {
      if (userStore.user.name.length > 0) {
        basketStore.addProduct(
          product.value.id,
          product.value.imageUrl,
          product.value.title,
          product.value.titleForUrl,
          product.value.price,
          quontity
        );
      } else {
        document.getElementById("info_modal_bag").showModal();
      }
    }
  } else {
    basketStore.addProductQuantity(checkObjectAndIndex());
  }
};

onMounted(async () => {
  let id: string;
  if (typeof router.params.productName === "string")
    id = router.params.productName;
  const response = await getProductByTitle(id);
  if (response.title) {
    product.value = { ...response };
    const responseCategory = await getCategoryByProduct(response.id);
    category.value = { ...responseCategory.categoryDtoList };
  } else {
    toast.error("Не удалось получить данные", {
      autoClose: 3000,
    });
  }
});
</script>



<style scoped></style>