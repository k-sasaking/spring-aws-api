<template>
  <div class="create">
    <h1>You can add Sample Article</h1>
    <div><input v-model="article.postName" placeholder="投稿者名" /></div>
    <div><input v-model="article.postText"  placeholder="投稿内容" /></div>
    <div><input v-model="article.postImage"  placeholder="画像" /></div>
    <button>追加</button>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'sample',
  data(){
    return {
      article: {
        postName: '',
        postText: '',
        postImage: ''
      }
    }
  },
  methods: {
    addArticle(){
      const vm = this
      const params = {
        postName: this.article.postName,
        postText: this.article.postText,
        postImage: this.article.postImage
      }
      vm.$axios.post('', params)
      .then(response => {
        console.log(response.data)
        vm.$router.push('/sample')
      })
    }
  }
}
</script>
