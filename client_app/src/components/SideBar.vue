<template>
  <div
    class="sidebar z-[1001] fixed top-0 bottom-0 -left-2 ml-2 w-[300px] overflow-y-auto text-center bg-base-100 drop-shadow-2xl"
  >
    <div class="bg-[#02346f] h-[104px]">
      <div class="flex justify-end">
        <a
          @mouseover="isHovered = true"
          @mouseleave="isHovered = false"
          @click="close()"
          class="tooltip tooltip-left cursor-pointer"
          data-tip="Закрыть"
        >
          <img
            class="w-5 m-2"
            :src="
              isHovered
                ? '/public/images/logo/close-gold.png'
                : '/public/images/logo/close-blue.png'
            "
          />
        </a>
      </div>
      <a @click="close()" class="cursor-pointer flex justify-center">
        <img class="mx-5 -mt-2" src="/public/images/logo/logo-leti.svg" />
      </a>
    </div>
    <div class="p-5">
      <div class="flex my-3 justify-start  group relative">
        <a
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/') }"
          @click.stop="$router.push(`/`)"
          >Главная</a
        >
      </div> <hr>
      <div class="join join-vertical w-full">
        <div class="collapse mt-2 -ml-[12px] cursor-pointer text-start collapse-arrow join-item ">
          <input type="radio" name="my-accordion-4" class="cursor-pointer" :checked="isChecked" @click="isChecked = !isChecked" /> 
          <div class="collapse-title  hover:text-[#bb8d54] cursor-pointer ">
            Товары
          </div>
          <div class="collapse-content"> 
            <ul>
              <li
                class="list-disc ml-5	"
                v-for="category in categories"
                :key="category.id"
              >
                <a
                  @click.stop="$router.push(`/category/${category.titleUrl}`)"
                  class="flex hover:text-[#bb8d54] cursor-pointer items-center gap-2 p-2"
                >
                  <span class="group-1-hover-font">{{ category.title }}</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <hr><div class="flex my-3 justify-start">
        <a
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/order') }"
          @click.stop="$router.push(`/order`)"
          >Заказы</a
        >
      </div> <hr>
      <div class="flex my-3 justify-start">
        <a
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/questions') }"
          @click.stop="$router.push(`/questions`)"
          >Вопросы</a
        >
      </div> <hr>
      <div class="flex my-3 justify-start">
        <a
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/contacts') }"
          @click.stop="$router.push(`/contacts`)"
          >Контакты</a
        >
      </div> <hr>
      <div class="flex my-3 justify-start">
        <a
          class="hover:text-[#bb8d54] cursor-pointer"
          :class="{ 'text-[#bb8d54] font-semibold': isActive('/user') }"
          @click.stop="$router.push(`/user`)"
          >О себе</a
        >
      </div>
    </div>
  
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, getCurrentInstance } from "vue";

import { getСategory, Category } from "../hooks/useCategory";
import { useRoute } from "vue-router"; 

const router = useRoute();
let categories = ref<Category[]>([]);

const isHovered = ref(false);
const isChecked = ref(false);

const instance = getCurrentInstance();

const isActive = (path: any) => {
  return router.path === path;
};

const close = () => {
  instance.emit("visible", true);
};
onMounted(async () => {
  const response = await getСategory();
  categories.value = { ...response };
});
</script>