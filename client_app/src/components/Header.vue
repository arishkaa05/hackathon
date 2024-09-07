
<template>
  <div>
  <div class="navbar z-[1001] sticky top-0 shadow-xl bg-[#02346f]">
    <div class="container max-w-screen-lg mx-auto">
      <div class="md:hidden block w-full">
        <a
          @click.stop="$router.push('/')"
          class="cursor-pointer h-10 flex justify-center"
        >
          <img v-if="!isVisible" src="/public/images/logo/logo-leti.svg" />
        </a>
        <div class="navbar-start mt-1 w-full">
          <side-bar v-if="isVisible" @visible="handleVisible" />
          <div class="flex justify-between">
            <div class="" @click="handleVisible">
              <label tabindex="0" class="btn btn-ghost btn-circle">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-8 w-8 hover:stroke-gray-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="white"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M4 6h16M4 12h16M4 18h7"
                  />
                </svg>
              </label>
            </div>
            <div class="flex">
            <modal-info-like v-if="userStore.user.name.length === 0"/>
            <modal-info-bag  v-if="userStore.user.name.length === 0" />
            <div v-if="userStore.user.name.length > 0"
              class="relative cursor-pointer"
              @mouseover="isHoveredLike = true"
              @mouseleave="isHoveredLike = false"
            >
              <a class="tooltip tooltip-bottom  cursor-pointer " @click.stop="$router.push('/favorite')" data-tip="Избранное">
                <img class="w-8 m-2" :src="isHoveredLike ? '/public/images/logo/like-gold.png' : '/public/images/logo/like-white.png'" />
              </a>
              <span
                class="badge absolute top-[4px] right-[6px] h-[15px] min-w-[15px] px-[2px]"
                >{{ favoriteStore.favorite.length }}</span
              >
            </div>
            <div class="relative cursor-pointer" v-if="userStore.user.name.length > 0"> 
              <a
                @mouseover="isHoveredBag = true"
                @mouseleave="isHoveredBag = false"
                @click.stop="$router.push('/basket')"
                class="tooltip tooltip-bottom cursor-pointer"
                data-tip="Корзина"
              >
                <img
                  class="w-8 m-2"
                  :src="
                    isHoveredBag
                      ? '/public/images/logo/bag-gold.png'
                      : '/public/images/logo/bag-white.png'
                  "
                />
              </a>
              <span
                class="badge absolute top-[4px] right-[6px] h-[15px] min-w-[15px] px-[2px]"
                >{{ basketStore.basket.length }}</span
              >
            </div>
            <div class="dropdown dropdown-hover">
              <a
                @mouseover="isHoveredUser = true"
                @mouseleave="isHoveredUser = false"
                class="cursor-pointer mb-2"
              >
                <img
                  class="w-10 mx-2 mt-1"
                  :src="
                    isHoveredUser
                      ? '/public/images/logo/user-gold.png'
                      : '/public/images/logo/user-white.png'
                  "
                />
              </a>
              <div
                class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-56 left-auto right-0"
              >
                <div class="flex mb-2">
                  <div class="w-12 h-12 mt-2 rounded-full border-2 mr-2 p-2">
                    <img
                      class="w-8 border-full"
                      :src="'/public/images/user/' + userStore.user.img + '.png'"
                    />
                  </div>
                  <div>
                    <h2 class="text-2xl mt-2 font-semibold">
                      {{ userStore.user.name }}
                    </h2>
                    <span class="text-xs -mt-2 text-gray-400">{{
                      userStore.user.email
                    }}</span>
                  </div>
                </div>
                <hr class="my-2" />
                <a
                  class="text-center text-gray-500 hover:text-[#bb8d54] cursor-pointer font-semibold"
                  @click="logout()"
                  v-if="userStore.user.name.length > 0"
                  >Выйти</a
                >
                <a
                  class="text-center text-gray-500 hover:text-[#bb8d54] cursor-pointer font-semibold"
                  @click.stop="$router.push('/login')"
                  v-if="userStore.user.name.length == 0"
                  >Войти</a
                >
              </div>
            </div>
          </div>
          </div>
        </div>
      </div>

      <div class="hidden md:block w-full">
        <div class="navbar-start w-full flex justify-between">
          <a
            @click.stop="$router.push('/')"
            class="cursor-pointer flex justify-center"
          >
            <img class="w-48" src="/public/images/logo/logo-leti.svg" />
          </a>
          <div class="flex mt-2 justify-between pl-2 h-8 items-center">
            <input
              type="text"
              v-model="userStore.searchInput"
              @input="searchProduct"
              placeholder="Введите название или описание товара"
              class="input mt-2 bg-[#1a5aa696] input-sm max-w-3xl w-96 hover:border-white focus:border-white text-white rounded-xl"
            />
            <svg
              class="pointer-events-none z-10 mt-2 -ml-8 stroke-current opacity-60 font-semibold text-white"
              width="20"
              height="20"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              ></path>
            </svg>
          </div>
          <div class="flex mt-2">
            <modal-info-like v-if="userStore.user.name.length === 0"/>
            <modal-info-bag  v-if="userStore.user.name.length === 0" />
            <div v-if="userStore.user.name.length > 0"
              class="relative cursor-pointer"
              @mouseover="isHoveredLike = true"
              @mouseleave="isHoveredLike = false"
            >
              <a class="tooltip tooltip-bottom  cursor-pointer " @click.stop="$router.push('/favorite')" data-tip="Избранное">
                <img class="w-8 m-2" :src="isHoveredLike ? '/public/images/logo/like-gold.png' : '/public/images/logo/like-white.png'" />
              </a>
              <span
                class="badge absolute top-[4px] right-[6px] h-[15px] min-w-[15px] px-[2px]"
                >{{ favoriteStore.favorite.length }}</span
              >
            </div>
            <div class="relative cursor-pointer" v-if="userStore.user.name.length > 0"> 
              <a
                @mouseover="isHoveredBag = true"
                @mouseleave="isHoveredBag = false"
                @click.stop="$router.push('/basket')"
                class="tooltip tooltip-bottom cursor-pointer"
                data-tip="Корзина"
              >
                <img
                  class="w-8 m-2"
                  :src="
                    isHoveredBag
                      ? '/public/images/logo/bag-gold.png'
                      : '/public/images/logo/bag-white.png'
                  "
                />
              </a>
              <span
                class="badge absolute top-[4px] right-[6px] h-[15px] min-w-[15px] px-[2px]"
                >{{ basketStore.basket.length }}</span
              >
            </div>
            <div class="dropdown dropdown-hover">
              <a
                @mouseover="isHoveredUser = true"
                @mouseleave="isHoveredUser = false"
                class="cursor-pointer mb-2"
              >
                <img
                  class="w-10 mx-2 mt-1"
                  :src="
                    isHoveredUser
                      ? '/public/images/logo/user-gold.png'
                      : '/public/images/logo/user-white.png'
                  "
                />
              </a>
              <div
                class="dropdown-content z-[1] menu p-2 shadow bg-base-100 rounded-box w-56 left-auto right-0"
              >
                <div class="flex mb-2">
                  <div class="w-12 h-12 mt-2 rounded-full border-2 mr-2 p-2">
                    <img
                      class="w-8 border-full"
                      :src="'/public/images/user/' + userStore.user.img + '.png'"
                    />
                  </div>
                  <div>
                    <h2 class="text-2xl mt-2 font-semibold">
                      {{ userStore.user.name }}
                    </h2>
                    <span class="text-xs -mt-2 text-gray-400">{{
                      userStore.user.email
                    }}</span>
                  </div>
                </div>
                <hr class="my-2" />
                <a
                  class="text-center text-gray-500 hover:text-[#bb8d54] cursor-pointer font-semibold"
                  @click="logout()"
                  v-if="userStore.user.name.length > 0"
                  >Выйти</a
                >
                <a
                  class="text-center text-gray-500 hover:text-[#bb8d54] cursor-pointer font-semibold"
                  @click.stop="$router.push('/login')"
                  v-if="userStore.user.name.length == 0"
                  >Войти</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    <Navbar />
  </div>
</template>

<script setup lang="ts">
import SideBar from "./SideBar.vue";
import UserInfo from "./UserInfo.vue";
import ModalInfoLike from './ModalInfoLike.vue'
import ModalInfoBag from './ModalInfoBag.vue'
import Navbar from './Navbar.vue'
import { useFavoriteStore } from "../stores/favoriteStore";
import { useBasketStore } from "../stores/basketStore";
import { useUserStore } from "../stores/userStore";
import { computed, ref, reactive, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { Product } from "../hooks/useProduct";
import { getBucket } from "../hooks/useBasket";
import { getFavorite } from "../hooks/useFavorite";


const router = useRouter();

const userStore = useUserStore();
const isHoveredBag = ref(false);
const isHoveredLike = ref(false);
const isHoveredUser = ref(false);

const favoriteStore = useFavoriteStore();
const basketStore = useBasketStore();

const isVisible = ref<Boolean>(false);

const searchProduct = () => {
  if (userStore.searchInput.length > 0) {
    setTimeout(() => {
      router.push("/search");
    })
  } else {
    setTimeout(() => {
      router.push("/");
    })
  }
}

const logout = () => {
  userStore.deleteUserData();
  localStorage.removeItem('jwt');
  localStorage.removeItem('user');
}

const handleVisible = () => {
  isVisible.value = !isVisible.value;
};

const redirectToFavorite= () => {
  if (userStore.user.name.length > 0) {
    router.push('/favorite');
  }  
};


onMounted(async () => {
  let user = localStorage.getItem( "user");
  if (user) {
    let obj = JSON.parse(user);
    userStore.setUserData(obj.id, "user", obj.name, obj.img, obj.email);
    const res = await getBucket(obj.id);
     if (res.productDtos && basketStore.basket.length === 0)
    res.productDtos.forEach((element: Product) => {
      basketStore.addProduct(element.id, element.imageUrl ,element.title, element.price, 1)
    })

    const resLike = await getFavorite(obj.id);
     if (resLike.productDtoList && favoriteStore.favorite.length === 0)
    resLike.productDtoList.forEach((element: Product) => {
      favoriteStore.addProduct(element.id, element.imageUrl ,element.title, element.price)
    })
  }
})


</script>