
<template>
  <div>
    <button
      @click="isOpen = !isOpen"
      class="w-full mt-6 text-white bg-[#02346f] hover:bg-[#bb8d54] focus:ring-4 focus:outline-none transition-all-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
    >
      Заказать
    </button>

    <dialog id="my_modal_4" class="modal" :open="isOpen">
      <form method="dialog" @submit.prevent="sendData()" class="modal-box">
        <button
          class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2"
          @click="isOpen = !isOpen"
        >
          ✕
        </button>
        <h2 class="block text-2xl mt-3 mb-6 font-bold">
          Пожалуйста, заполните поля
        </h2>
        <div class="mb-3">
          <label for="text" class="block mb-2 text-sm font-medium text-gray-900"
            >Адрес</label
          >
          <input
            v-model="place"
            type="text"
            name=""
            id="place"
            class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
            placeholder="ул. Профессора Попова, 5"
            required=""
          />
        </div>
        <div>
          <label
            for="comment"
            class="block mb-2 text-sm font-medium text-gray-900"
            >Комментарий</label
          >
          <textarea
            v-model="comment"
            class="textarea bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
            placeholder="Ваш комментарий"
            rows="3"
          ></textarea>
        </div>
        <div class="flex justify-between mt-6">
          <div class="text-sm text-gray-500 font-semibold">
            Cтоимость доставки:
          </div>
          <div class="text-xl text-gray-500 font-semibold">
            {{ setDeliveryPrice() }}₽
          </div>
        </div>
        <div class="modal-action">
          <button
            type="submit"
            class="w-full text-white bg-[#02346f] hover:bg-[#bb8d54] focus:ring-4 focus:outline-none transition-all-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          >
            Заказать
          </button>
        </div>
      </form>
      <form method="dialog" class="modal-backdrop">
        <button @click="isOpen = !isOpen">close</button>
      </form>
    </dialog>
  </div>
</template>


<script setup lang='ts'>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useBasketStore } from "../stores/basketStore";
import { useUserStore } from "../stores/userStore";
import { createOrder } from "../hooks/useOrder";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

const router = useRouter();

let place = ref("");
let comment = ref("");

let isOpen = ref<Boolean>(false);

const userStore = useUserStore();
const basketStore = useBasketStore();

const setDeliveryPrice = () => {
  if (basketStore.getFullPrice() > 1000) return 0;
  else return 300;
};

const sendData = async () => {
  const result = await createOrder(
    String(userStore.user.id),
    basketStore.getAllProducts(),
    place.value,
    comment.value,
    setDeliveryPrice()
  );
  if (result.userId) {
    basketStore.clearBasket();
    toast.success("Заказ успешно оформлен", {
      autoClose: 3000,
    });
  } else {
    toast.error("Не получилось сделать заказ", {
      autoClose: 3000,
    });
  }
  isOpen.value = false;
};
</script>

<style>
</style>