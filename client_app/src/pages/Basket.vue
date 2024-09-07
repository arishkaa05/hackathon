<template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Корзина
      </h1>
    </div>
    <div class="card w-full bg-white shadow-xl">
      <div class="card-body">
        <div class="my-20" v-if="basketStore.basket.length === 0">
          <h3 class="text-center mb-3 text-xl text-[#02346f] font-bold">
            Корзина пуста
          </h3>
          <div class="flex justify-center">
            <a
              @click.stop="$router.push('/category')"
              class="text-3xl cursor-pointer hover:text-[#bb8d54] text-[#02346f] font-bold"
              >За покупками!</a
            >
          </div>
        </div>
        <div
          class="w-full lg:flex"
          v-for="(product, index) in basketStore.basket"
          :key="product.id"
        >
          <div class="rounded-lg flex justify-center border-2 lg:w-20 p-3 me-4">
            <img
              class="md:w-16 max-w-xs"
              :src="'/public/images/product/' + product.imageUrl"
              :alt="product.title"
            />
          </div>
          <div class="sm:flex items-center w-full justify-between">
            <div class="lg:w-1/2 my-3 lg:my-auto me-4">
              <a class="cursor-pointer flex justify-between hover:text-[#bb8d54]" @click.stop="$router.push(`/product/${product.titleUrl}`)">  <h3 class="font-semibold text-xl sm:text-start text-center">
                {{ product.title }}
              </h3></a>
            </div>
            <div class="flex lg:w-64 my-3 justify-between align-center">
              <div
                class="quantity w-16 me-3 h-8 inline-flex rounded-lg bg-white shadow"
              >
                <input
                  class="quantity-value input-number w-12 border-none bg-transparent p-1 text-center text-lg text-gray-400 focus:border-none focus:ring-0"
                  :value="product.quantity"
                />
                <div class="flex w-5 flex-col justify-between">
                  <button
                    @click="basketStore.addProductQuantity(index)"
                    class="quantity-btn increment text-primary-color"
                    type="button"
                  >
                    <img
                      class="w-3 mt-1"
                      src="/public/images/logo/up-blue.png"
                    />
                  </button>
                  <button
                    @click="basketStore.decreaseProductQuantity(index)"
                    class="quantity-btn decrement text-primary-color"
                    type="button"
                  >
                    <img
                      class="w-3 mb-1"
                      src="/public/images/logo/down-blue.png"
                    />
                  </button>
                </div>
              </div>
              <div class="font-semibold mr-3 mt-1 text-md text-[#02346f]">
                {{ product.price }}₽
              </div>
              <div class="font-semibold text-xl text-[#02346f]">
                {{ product.price * product.quantity }}₽
              </div>
              <a
                @mouseover="isHovered = true"
                @mouseleave="isHovered = false"
                @click="basketStore.removeProduct(index)"
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
        <hr>
        <div class="flex justify-between mt-6"> <div class="text-sm text-gray-500 font-semibold">Сумма заказа:</div><div class="text-xl text-gray-500 font-semibold">{{basketStore.getFullPrice()}}₽</div></div>


          <ModalOrder
            v-if="basketStore.basket.length > 0" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useBasketStore } from "../stores/basketStore";
import ModalOrder from '../components/ModalOrder.vue'

const isHovered = ref(false);
const currentPage = ref(1);

const basketStore = useBasketStore();
</script>

<style scoped> 
</style>