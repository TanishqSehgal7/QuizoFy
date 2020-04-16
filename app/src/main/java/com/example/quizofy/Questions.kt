package com.example.quizofy

class Questions (

    private var category:String,
    private  var artLit:String,
    private  var entertainment:String,
    private  var generalknowledge:String,
    private  var Geography:String,
    private  var History:String,
    private  var Music:String,
    private  var Politics:String,
    private  var sneakerCulture:String,
    private  var sports:String,
    private  var technology:String,
    private  var Ques1:String,
    private  var Ques2:String,
    private  var Ques3:String,
   private  var Ques4:String,
    private var Ques5:String,
    private  var option1:String,
    private  var option2:String,
    private  var option3:String,
    private  var option4:String,
    private  var correct:String,
    private  var question:String) :Comparable<Questions> {

    constructor(): this("","","","","","","","",
    "","","","","","","","","","","",
    "","","")

    init {
        this.category = category
        this.artLit = artLit
        this.entertainment = entertainment
        this.generalknowledge = generalknowledge
        this.Geography = Geography
        this.History = History
        this.Music = Music
        this.Politics = Politics
        this.sneakerCulture = sneakerCulture
        this.sports = sports
        this.technology = technology
        this.Ques1 = Ques1
        this.Ques2 = Ques2
        this.Ques3 = Ques3
        this.Ques4=Ques4
        this.Ques5 = Ques5
        this.option1 = option1
        this.option2 = option2
        this.option3 = option3
        this.option4 = option4
        this.correct = correct
        this.question = question
    }

    override fun compareTo(other: Questions): Int {
        return 0
    }
}


