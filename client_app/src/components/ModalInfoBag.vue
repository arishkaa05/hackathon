
<template>
  <div>
     <a
      class="tooltip tooltip-bottom cursor-pointer"
      @mouseover="isHovered = true"
      @mouseleave="isHovered = false"
      @click="isOpen = !isOpen"
      data-tip="Корзина"
    >
      <img
        class="w-8 m-2"
        :src="
          isHovered
            ? '/public/images/logo/bag-gold.png'
            : '/public/images/logo/bag-white.png'
        "
      />
    </a>
    <dialog id="my_modal_bag" class="modal" :open="isOpen">
      <form method="dialog" class="modal-box">
        <button
          class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2"
          @click="isOpen = !isOpen"
        >
          ✕
        </button>
        <p class="py-8 text-gray-500 text-lg text-center">
          Для доступа к разделу "Корзина" необходимо
          <a
            class="cursor-pointer text-[#02346f] hover:text-[#bb8d54]"
            @click="onLogin()"
            >авторизоваться</a
          >.
        </p>
        <div class="modal-action">
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

const router = useRouter();

let isOpen = ref<Boolean>(false);


const onLogin = () => {
  router.push("/login");
  isOpen.value = false;
};


</script>

<style>
</style>