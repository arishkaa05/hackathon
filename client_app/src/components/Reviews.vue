<template>
  <div>
    <div
      class="bg-[#02346f] text-white px-5 text-xl font-semibold uppercase py-3"
    >
      Отзывы
    </div>
    <div
      v-if="commentList && commentList.length > 0"
      class="grid gap-5 rounded-lg bg-white p-2 xs:p-8"
    >
      <div class="md:flex justify-center w-full">
        <div class="my-auto text-center">
          <h2 class="text-7xl text-warning font-semibold mx-5">
            {{ calculateAverageRating() }}
          </h2>
          <span class="text-md text-[#02346f] font-semibold mx-7"
            >{{ commentList.length }} отзывов</span
          >
        </div>
        <div class="my-6 md:w-1/2">
          <div class="mb-2" v-for="i in [5, 4, 3, 2, 1]" :key="i">
            <span class="text-gray-500 font-semibold mx-3">{{ i }} </span
            ><progress
              class="progress progress-warning -mb-1 h-4 w-3/4"
              :value="(getCommentCountByRating(i) * 100) / commentList.length"
              max="100"
            ></progress>
          </div>
        </div>
      </div>

      <hr />
      <div class="flex mb-4 p-2">
        <div
          class="w-16 h-16 mt-2 rounded-full border-2 mr-2 p-2"
          style="min-width: 64px"
        >
          <img
            class="w-12 border-full"
            :src="'/public/images/user/' + userStore.user.img + '.png'"
          />
        </div>

        <div class="w-full">
          <h3 class="font-semibold text-lg">Добавьте отзыв</h3>
          <div class="relative overflow-hidden inline-block">
            <span
              v-for="star in [1, 2, 3, 4, 5]"
              :key="star"
              style="flex-shrink: 0"
              @mousemove="setRating(star)"
              class="cursor-pointer inline-block w-4 h-4 mr-1 bg-cover bg-[url('/src/assets/logo/star-outline.png')]"
            >
            </span>

            <div
              :style="{ width: rating * 20 + '%' }"
              class="cursor-pointer inline-block absolute w-0 h-full t-[2px] left-0 right-0 overflow-hidden"
            >
              <span
                v-for="star in [1, 2, 3, 4, 5]"
                :key="star"
                @mousemove="setRating(star)"
                style="flex-shrink: 0"
                class="inline-block w-4 h-4 mr-1 bg-cover bg-[url('/src/assets/logo/star-fill.png')]"
              >
              </span>
            </div>
          </div>
          <div class="w-full">
            <textarea
              v-model="review"
              class="textarea w-full textarea-bordered"
              placeholder="Ваш отзыв"
            ></textarea>
          </div>
          <div class="flex justify-end">
            <button
              @click.prevent="addReview()"
              class="btn-effect relative flex btn transition-all-300 bg-[#02346f] border-[#02346f] hover:bg-[#bb8d54] hover:border-[#bb8d54] rounded-lg bg-primary-color py-2 px-8 text-white"
            >
              <span class="font-bold uppercase text-white">Добавить</span>
            </button>
          </div>
        </div>
      </div>
      <hr />
      <div
        class="flex mb-4 p-2"
        v-for="comment in commentList"
        :key="comment.id"
      >
        <div
          class="w-16 h-16 mt-2 rounded-full border-2 mr-2 p-2"
          style="min-width: 64px"
        >
          <img class="w-12 border-full" :src="'/public/images/user/' + comment.imageUrl + '.png'" />
        </div>

        <div>
          <h3 class="font-semibold text-lg">
            {{comment.username}} <span class="ml-3 text-gray-500 text-sm">{{formattedDate(comment.createDate)}}</span>
          </h3>
          <div class="relative overflow-hidden inline-block">
            <span
              v-for="star in [1, 2, 3, 4, 5]"
              :key="star"
              style="flex-shrink: 0"
              class="star inline-block w-3 h-3 mr-1 bg-cover bg-[url('/src/assets/logo/star-outline.png')]"
            >
            </span>
            <div
              :style="`width: ${57}%;`"
              class="star-rating__colored inline-block absolute w-0 h-full t-[2px] left-0 right-0 overflow-hidden"
            >
              <span
                v-for="star in [1, 2, 3, 4, 5]"
                :key="star"
                style="flex-shrink: 0"
                class="inline-block w-3 h-3 mr-1 bg-cover bg-[url('/src/assets/logo/star-fill.png')]"
              >
              </span>
            </div>
          </div>
          <div >
            {{comment.message}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
import { onMounted, ref, getCurrentInstance } from "vue";
import { getСomment, createСomment, Сomment } from "../hooks/useComment";
import { useRoute } from "vue-router";
import { useUserStore } from "../stores/userStore";
import { useBasketStore } from "../stores/basketStore";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

const props = defineProps({
  id: {
    type: String,
  },
});

const userStore = useUserStore();
const instance = getCurrentInstance();

let commentList = ref<Сomment[]>();
let category = ref({});
let rating = ref(0);
let review = ref("");

let quontity = ref(0);

const getCommentCountByRating = (rating: number) => {
  if (Array.isArray(commentList.value)) {
    return commentList.value.filter((comment) => comment.rating === rating)
      .length;
  } else {
    return 0;
  }
};

const calculateAverageRating = () => {
  if (commentList.value.length === 0) {
    instance.emit("rating", 0);
    return 0;
    
  } else {
    const sum = commentList.value.reduce((total, comment) => {
      return total + (comment.rating || 0);
    }, 0);
    instance.emit("rating", (sum / commentList.value.length).toFixed(1));
    return (sum / commentList.value.length).toFixed(1);
  }
};

const addReview = async () => {
   if (rating.value > 0) {
    const response = await createСomment(
      props.id,
      userStore.user.id,
      review.value,
      rating.value
    );
    if (response) {
      review.value = '';
      rating.value = 0;
    }
  } else {
    toast.error("Оцените продукт!", {
      autoClose: 3000,
    });
  }
};

const setRating = (star: number) => {
  rating.value = star;
};

const formattedDate = (createdDate: string) => {
  const date = new Date(createdDate);
  const options = {
    year: 'numeric' as const,
    month: '2-digit' as const,
    day: '2-digit' as const
  };
  return date.toLocaleDateString('en-US', options).replace(/\//g, '.');
};

onMounted(async () => {
  const response = await getСomment(props.id);
  if (response.length > 0) {
    commentList.value = response;
  }
});
</script>



<style scoped></style>