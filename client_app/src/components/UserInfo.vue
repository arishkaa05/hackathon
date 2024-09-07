<template>
  <div class="card w-full bg-base-100 shadow-xl">
    <div class="card-body">
      <h2 class="block text-2xl mb-3 font-bold">Мой аккаунт</h2>
      <form class="space-y-4 md:space-y-6" @submit.prevent="sendData()">
        <div class="grid grid-cols-3 gap-4">
          <div
              class="rounded-full flex border-2 p-2 px-4"
              v-for="img in userImg"
              :key="img"
          >
            <img class="w-8 mr-3" :src="'/public/images/user/' + img + '.png'" />
            <input
                type="radio"
                name="radio-1"
                :value="img"
                v-model="selectedImg"
                class="radio checked:bg-[#02346f] mt-1"
            />
          </div>
        </div>
        <div class="grid grid-cols-2 gap-4">

          <div>
            <label
                for="username"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Имя</label
            >
            <input
                v-model="username"
                type="text"
                name="username"
                id="username"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                placeholder="Иван"
            />
          </div>
          <div>
            <label
                for="lastname"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Фамилия</label
            >
            <input
                v-model="lastname"
                type="text"
                name="lastname"
                id="lastname"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                placeholder="Иванов"
            />
          </div>
          <div>
            <label
                for="email"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Email</label
            >
            <input
                v-model="email"
                type="text"
                name="email"
                id="email"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                placeholder="name@etu.ru"
            />
          </div>
          <div>
            <label
                for="city"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Город</label
            >
            <input
                v-model="city"
                type="text"
                name="city"
                id="city"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                placeholder="Москва"
            />
          </div>
          <div>
            <label
                for="contactNumber"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Телефон</label
            >
            <input
                v-model="contactNumber"
                type="text"
                name="contactNumber"
                id="contactNumber"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                placeholder="+7 (999) 99 99"
            />
          </div>
          <div>
            <label
                for="contactNumber"
                class="block mb-2 text-sm font-medium text-gray-900"
            >Пароль</label
            >
            <input
                v-model="password"
                type="password"
                name="password"
                id="password"
                placeholder="••••••••"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
            />
          </div>
        </div>
        <button
            type="submit"
            class="w-full text-white bg-[#02346f] hover:bg-[#bb8d54] focus:ring-4 focus:outline-none transition-all-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
        >
          Внести изменения
        </button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {getCurrentInstance} from "vue";

import "vue3-toastify/dist/index.css";
import {ref} from "vue";
import {updateUser, User} from "../hooks/useUser.ts";
import {toast} from "vue3-toastify";
import {useUserStore} from "../stores/userStore.ts";
import "vue3-toastify/dist/index.css";
const instance = getCurrentInstance();
const userStore = useUserStore();


const userImg = ["beaver", "bee", "duck", "elephant", "orca", "turtle"];

let passwordNotConfirmed = ref(false);
let errorMsg = ref("");
let username = ref("");
let lastname = ref("");
let email = ref("");
let city = ref("");
let contactNumber = ref("");
let password = ref("");
let passwordConfirmation = ref("");
let selectedImg = ref(userImg[0]);

const isSign = () => {
  instance.emit("userUpdate", true);
};
const sendData = async () => {
  errorMsg.value = "";

    passwordNotConfirmed.value = false;
    const newUserData: User = {
      id: userStore.user.id,
      username: username.value,
      lastname: lastname.value,
      email: email.value,
      contactNumber: contactNumber.value,
      city: city.value,
      imageUrl:selectedImg.value,
      password:password.value,
      passwordConfirmation:password.value
    };
    const result = await updateUser(newUserData);
    if (result && result.title) {
      toast.success("Вы успешно обновили личный кабинет!.", {
        autoClose: 3000,
      });
      setTimeout(() => {
        isSign();
      }, 1000);
    } else {
      toast.error('Не удалось обновить данные', {
        autoClose: 3000,
      });
    }

};
</script>