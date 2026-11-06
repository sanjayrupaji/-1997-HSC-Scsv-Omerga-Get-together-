package com.example.data.local

import com.example.data.model.CollegeMemoryItem
import com.example.data.model.CommitteeMember

object InitialDataLoader {

    fun getSampleClassmates(): List<ClassmateEntity> {
        return listOf(
            ClassmateEntity(
                fullName = "संजय वसंतराव कोरे",
                nickname = "संज्या",
                stream = "विज्ञान (Science)",
                occupationCategory = "व्यवसाय / उद्योग",
                jobTitle = "संचालक - शिवकृपा ॲग्रो एजन्सी",
                organization = "शिवकृपा ॲग्रो व फर्टिलायझर्स, उमरगा",
                nativeVillage = "मुरुम (ता. उमरगा)",
                currentCity = "उमरगा",
                address = "मेन रोड, बस स्टँड जवळ, उमरगा, जि. धाराशिव",
                phone = "9822451230",
                whatsapp = "9822451230",
                email = "sanjay.kore97@gmail.com",
                bloodGroup = "O+ve",
                thoughtsAndMemories = "आपल्या शिवाजी महाविद्यालयाच्या त्या सुंदर दिवसांची सर कशालाच नाही. कट्ट्यावर बसून केलेली भविष्याची स्वप्ने आज खरी होताना पाहताना मन भरून येते. १९९७ च्या सर्व मित्रांना पुन्हा एकदा एकत्र भेटण्याचा आनंद शब्दातीत आहे!",
                favoriteMemory = "बारावीच्या परीक्षेच्या आधी कॉलेज कट्ट्यावर केलेली ग्रुप स्टडी आणि चहा!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 0,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "डॉ. सचिन प्रभाकर पाटील",
                nickname = "सचिन",
                stream = "विज्ञान (Science)",
                occupationCategory = "वैद्यकीय / डॉक्टर",
                jobTitle = "एम.डी. मेडिसिन (कन्सल्टंट फिजिशियन)",
                organization = "संजीवनी मल्टीस्पेशालिटी हॉस्पिटल, सोलापूर",
                nativeVillage = "गुंजोटी (ता. उमरगा)",
                currentCity = "सोलापूर",
                address = "हॉटगी रोड, सोलापूर",
                phone = "9422034567",
                whatsapp = "9422034567",
                email = "dr.sachinpatil@gmail.com",
                bloodGroup = "B+ve",
                thoughtsAndMemories = "शालेय आणि महाविद्यालयीन जीवनात शिवाजी कॉलेजने मला घडवले. आमचे जीवशास्त्र (Biology) आणि रसायनशास्त्र (Chemistry) चे प्रॅक्टिकलचे तास आजही ताजे आहेत. सर्व मित्रांना भेटण्यासाठी मी आवर्जून येत आहे.",
                favoriteMemory = "१९९६ मधील सायन्स एक्झिबिशन मधील आपला पहिला क्रमांक!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 1,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "प्रा. अमर ज्ञानोबा सूर्यवंशी",
                nickname = "अमर",
                stream = "कला (Arts)",
                occupationCategory = "शिक्षण / प्राध्यापक",
                jobTitle = "सहयोगी प्राध्यापक (मराठी विभाग)",
                organization = "दयानंद कला महाविद्यालय, लातूर",
                nativeVillage = "येणेगूर (ता. उमरगा)",
                currentCity = "लातूर",
                address = "कवठा रोड, लातूर",
                phone = "9890123478",
                whatsapp = "9890123478",
                email = "amar.suryawanshi@gmail.com",
                bloodGroup = "A+ve",
                thoughtsAndMemories = "आपल्या वर्गातील मैत्री ही निस्वार्थी आणि अजरामर होती. आज २५ वर्षांपेक्षा जास्त काळ लोटला तरी ते दिवस आठवले की अंगावर रोमांच उभे राहतात. या गेट-टुगेदरच्या निमित्ताने सर्व आठवणींना उजाळा मिळेल.",
                favoriteMemory = "वार्षिक स्नेहसंमेलनात सादर केलेले 'शांतता कोर्ट चालू आहे' नाटक!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 2,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "राहुल विश्वनाथ गायकवाड",
                nickname = "राहुल्या",
                stream = "विज्ञान (Science)",
                occupationCategory = "अभियांत्रिकी / IT",
                jobTitle = "सिनियर क्लाऊड आर्किटेक्ट",
                organization = "इन्फोसिस लिमिटेड, हिंजवडी, पुणे",
                nativeVillage = "उमरगा",
                currentCity = "पुणे",
                address = "बाणेर पाषाण लिंक रोड, पुणे",
                phone = "9765432109",
                whatsapp = "9765432109",
                email = "rahul.gaikwad97@yahoo.com",
                bloodGroup = "AB+ve",
                thoughtsAndMemories = "उमरग्यापासून पुण्यापर्यंतच्या प्रवासात शिवाजी कॉलेजने दिलेला आत्मविश्वास नेहमी पाठीशी राहिला. त्या काळी मोबाईल नव्हते तरी सायकलवरून सर्व मित्रांच्या घरी जाऊन केलेली मौजमजा अवर्णनीय होती.",
                favoriteMemory = "सायकल स्टँडवर सायकल लॉक अडकल्याचा तो गमतीशीर प्रसंग!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 3,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "सौ. सुवर्णा विनायक कुलकर्णी (जोशी)",
                nickname = "सुवर्णा",
                stream = "कला (Arts)",
                occupationCategory = "शिक्षण / प्राध्यापक",
                jobTitle = "माध्यमिक शिक्षिका",
                organization = "जिल्हा परिषद हायस्कूल, धाराशिव (उस्मानाबाद)",
                nativeVillage = "आलूर (ता. उमरगा)",
                currentCity = "धाराशिव",
                address = "आनंदनगर, धाराशिव",
                phone = "9404112233",
                whatsapp = "9404112233",
                email = "suvarna.kulkarni@gmail.com",
                bloodGroup = "O+ve",
                thoughtsAndMemories = "मुलींसाठी त्या काळात बारावी शिक्षण घेताना मैत्रिणींची खूप मोठी साथ लाभली. आमच्या ग्रुपमधील त्या गप्पा आणि डबे वाटून खाणे मला आजही आठवते. सर्व मैत्रिणींना आणि मित्रांना भेटण्यासाठी खूप उत्सुक आहे.",
                favoriteMemory = "कॉलेजच्या वाचनालयातील शांतता आणि एकत्र बसून केलेला अभ्यास!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 4,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "विजयकुमार सिद्राम बिराजदार",
                nickname = "विजू",
                stream = "वाणिज्य (Commerce)",
                occupationCategory = "शासकीय सेवा",
                jobTitle = "नायब तहसीलदार (महसूल विभाग)",
                organization = "विभागीय आयुक्त कार्यालय, छत्रपती संभाजीनगर",
                nativeVillage = "दाळिंब (ता. उमरगा)",
                currentCity = "छत्रपती संभाजीनगर",
                address = "सिडको एन-४, छत्रपती संभाजीनगर",
                phone = "9823998877",
                whatsapp = "9823998877",
                email = "vijay.birajdar@maharashtra.gov.in",
                bloodGroup = "B+ve",
                thoughtsAndMemories = "कॉलेज जीवनाने व्यक्तिमत्त्व घडवले. स्पर्धा परीक्षेची पहिली प्रेरणा मला आपल्याच शिवाजी कॉलेजच्या ग्रंथालयाने दिली. उमरग्याचे जुने दिवस आणि आमची बारावीची बॅच माझ्या हृदयाच्या सर्वात जवळ आहे.",
                favoriteMemory = "कॉमर्स क्लासमधील अकाउंट्सच्या लेक्चर्स आणि सरांचे मार्गदर्शन!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 5,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "समाधान तुकाराम जाधव",
                nickname = "समा",
                stream = "कला (Arts)",
                occupationCategory = "शेती / कृषी व्यवसाय",
                jobTitle = "प्रगतीशील शेतकरी व कृषी उद्योजक",
                organization = "जाधव फार्म्स & डेअरी प्रॉडक्ट्स, मुरुम",
                nativeVillage = "मुरुम (ता. उमरगा)",
                currentCity = "मुरुम",
                address = "जाधव मळा, मुरुम रोड, जि. धाराशिव",
                phone = "9763221100",
                whatsapp = "9763221100",
                email = "samadhan.jadhav@gmail.com",
                bloodGroup = "A+ve",
                thoughtsAndMemories = "शेतीत आधुनिक तंत्रज्ञान वापरून आज यशस्वी झालो तरी कॉलेजचे दिवस आठवले की मनाला नवा हुरूप येतो. सर्व जुन्या दोस्तांना भेटण्याची ही संधी सोडूच शकत नाही. प्रत्येकाने आवर्जून यावे!",
                favoriteMemory = "स्पोर्ट्स डे मधील व्हॉलीबॉल आणि कबड्डीच्या जोरदार स्पर्धा!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 6,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "गजानन मारुती शिंदे",
                nickname = "गजाभाऊ",
                stream = "वाणिज्य (Commerce)",
                occupationCategory = "व्यवसाय / उद्योग",
                jobTitle = "बिल्डर & गव्हर्नमेंट कॉन्ट्रॅक्टर",
                organization = "शिंदे इन्फ्रास्ट्रक्चर प्रा. लि., पुणे व उमरगा",
                nativeVillage = "तुरोरी (ता. उमरगा)",
                currentCity = "पुणे",
                address = "कात्रज कोंढवा रोड, पुणे",
                phone = "9860124578",
                whatsapp = "9860124578",
                email = "gajanan.shinde@shindeinfra.com",
                bloodGroup = "O+ve",
                thoughtsAndMemories = "व्यवसायात कितीही व्यस्त असलो तरी बारावीच्या मित्रांचे प्रेम हे अमूल्य आहे. आपल्या उमरगा कॉलेजच्या भूमीला वंदन करण्यासाठी आणि मित्रांची गळाभेट घेण्यासाठी नक्की उपस्थित राहणार आहे.",
                favoriteMemory = "कॉलेजच्या समोरच्या टपरीवरील तो स्पेशल अद्रक चहा आणि हास्यविनोद!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 7,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "डॉ. प्रियांका रमेश देशमुख",
                nickname = "पियू",
                stream = "विज्ञान (Science)",
                occupationCategory = "वैद्यकीय / डॉक्टर",
                jobTitle = "स्त्रीरोग व प्रसूती तज्ज्ञ (MD, DGO)",
                organization = "मातृसेवा हॉस्पिटल, ठाणे (मुंबई)",
                nativeVillage = "उमरगा",
                currentCity = "मुंबई",
                address = "घोडबंदर रोड, ठाणे पश्चिम, मुंबई",
                phone = "9820556677",
                whatsapp = "9820556677",
                email = "dr.priyanka.deshmukh@gmail.com",
                bloodGroup = "A+ve",
                thoughtsAndMemories = "उमरग्यावरून निघून मुंबईत स्थायिक झाले तरी मनातील नाळ उमरगा आणि शिवाजी कॉलेजशी घट्ट जोडलेली आहे. सर्व मित्रांचे मनोगत वाचून खूप आनंद झाला. सर्वांना खूप खूप शुभेच्छा!",
                favoriteMemory = "जीवशास्त्राच्या प्रात्यक्षिकातील बेडूक डिसेक्शन आणि त्यावर उडालेली हसण्याची खसखस!",
                attendanceStatus = "नक्की नाही",
                avatarColorIndex = 1,
                isVerified = true
            ),
            ClassmateEntity(
                fullName = "दत्तात्रय लिंबाजी माने",
                nickname = "दत्तू",
                stream = "वाणिज्य (Commerce)",
                occupationCategory = "इतर",
                jobTitle = "शाखा व्यवस्थापक (Branch Manager)",
                organization = "स्टेट बँक ऑफ इंडिया (SBI), लातूर",
                nativeVillage = "पेठसांगवी (ता. उमरगा)",
                currentCity = "लातूर",
                address = "शिवाजी नगर, लातूर",
                phone = "9423187654",
                whatsapp = "9423187654",
                email = "dattatray.mane@sbi.co.in",
                bloodGroup = "B+ve",
                thoughtsAndMemories = "आपल्या शिवाजी महाविद्यालयाची शिस्त आणि संस्कार आजही माझ्या आयुष्याचा पाया आहेत. आपल्या १९९७ बॅचचे सर्व मित्र आज विविध क्षेत्रांत उत्तुंग भरारी घेत आहेत याचा सार्थ अभिमान वाटतो.",
                favoriteMemory = "बारावीच्या बोर्ड परीक्षेचा शेवटचा दिवस आणि कॉलेज आवारात घेतलेला निरोप!",
                attendanceStatus = "उपस्थित राहणार",
                avatarColorIndex = 2,
                isVerified = true
            )
        )
    }

    fun getSampleEvents(): List<EventEntity> {
        return listOf(
            EventEntity(
                timeSlot = "०९:०० AM - १०:०० AM",
                titleMarathi = "नोंदणी, स्वागत व मराठवाडी नाश्ता",
                titleEnglish = "Registration, Welcome & Breakfast",
                location = "मुख्य प्रवेशद्वार व स्वागत कक्ष, शिवाजी महाविद्यालय",
                descriptionMarathi = "सर्व माजी विद्यार्थ्यांचे सहर्ष स्वागत, १९९७ बॅच विशेष आय-कार्ड, फोल्डर व स्मरणिका किट वाटप. सोबत गरमागरम पोहे, उपमा, शिरा व स्पेशल चहा.",
                iconName = "coffee",
                isHighlight = false,
                orderIndex = 1
            ),
            EventEntity(
                timeSlot = "१०:०० AM - १०:३० AM",
                titleMarathi = "दीपप्रज्वलन व छत्रपती शिवाजी महाराज पूजन",
                titleEnglish = "Inauguration & Poojan",
                location = "महाविद्यालय मुख्य सभागृह (ऑडिटोरियम)",
                descriptionMarathi = "कार्यक्रमाचे औचित्य साधून छत्रपती शिवाजी महाराज यांच्या प्रतिमेस पुष्पहार अर्पण, दीपप्रज्वलन व महाविद्यालयीन गीत गायन.",
                iconName = "sparkles",
                isHighlight = true,
                orderIndex = 2
            ),
            EventEntity(
                timeSlot = "१०:३० AM - ११:३० AM",
                titleMarathi = "आदरणीय प्राध्यापकांचा सन्मान व गुरुवंदना",
                titleEnglish = "Teachers Felicitation & Guru Vandana",
                location = "महाविद्यालय मुख्य सभागृह",
                descriptionMarathi = "१९९५-१९९७ या काळात आपल्याला ज्ञानदान केलेल्या सर्व आदरणीय प्राचार्य व प्राध्यापकांचा शाल, श्रीफळ व मानचिन्ह देऊन कृतज्ञतापूर्वक सत्कार.",
                iconName = "school",
                isHighlight = true,
                orderIndex = 3
            ),
            EventEntity(
                timeSlot = "११:३० AM - ०१:०० PM",
                titleMarathi = "\"ते दिवस, ती मैत्री\" - वर्गमित्रांचा परिचय व मनोगत",
                titleEnglish = "Classmates Introduction & Nostalgia",
                location = "महाविद्यालय मुख्य सभागृह",
                descriptionMarathi = "प्रत्येक मित्राचा आजचा प्रवास, कुटुंब, कामाचे स्वरूप आणि बारावीच्या अविस्मरणीय आठवणींचे खुले मनोगत सत्र. हसणे, जुन्या खोड्या व भावुक क्षण.",
                iconName = "record_voice_over",
                isHighlight = true,
                orderIndex = 4
            ),
            EventEntity(
                timeSlot = "०१:०० PM - ०२:३० PM",
                titleMarathi = "मराठवाडी रुचकर स्नेहभोजन",
                titleEnglish = "Grand Reunion Lunch",
                location = "कॅम्पस डायनिंग हॉल / लॉन",
                descriptionMarathi = "मराठवाड्याची खास चव: गरमागरम बाजरी-ज्वारीची भाकरी, गावरान पिठलं, मटकी उसळ, शेवभाजी, गुलाबजाम, आमटी-भात व पापड-लोणचे.",
                iconName = "restaurant",
                isHighlight = false,
                orderIndex = 5
            ),
            EventEntity(
                timeSlot = "०२:३० PM - ०४:०० PM",
                titleMarathi = "जुन्या आठवणींचे क्षण, मिमिक्री व खेळ",
                titleEnglish = "Fun Games, Mimicry & Music",
                location = "ओपन एअर थिएटर / कॉलेज कट्टा",
                descriptionMarathi = "कॉलेजच्या काळातील गाणी, मित्रांची मिमिक्री, मनोरंजक खेळ, जुने किस्से व हास्याची कारंजी. आपल्या जुन्या वर्गांना व कट्ट्यांना भेट.",
                iconName = "celebration",
                isHighlight = false,
                orderIndex = 6
            ),
            EventEntity(
                timeSlot = "०४:०० PM - ०४:४५ PM",
                titleMarathi = "भव्य ग्रुप फोटो व स्मृतीचिन्ह वाटप",
                titleEnglish = "Batch Group Photo & Souvenir",
                location = "महाविद्यालयाची ऐतिहासिक मुख्य इमारत पायऱ्या",
                descriptionMarathi = "१९९७ बॅचचा अधिकृत ऐतिहासिक ग्रुप फोटो सेशन, प्रत्येकाला विशेष स्मृतीचिन्ह (Trophy / Plaque) व बॅच कॅलेंडर भेट.",
                iconName = "photo_camera",
                isHighlight = true,
                orderIndex = 7
            ),
            EventEntity(
                timeSlot = "०४:४५ PM - ०५:३० PM",
                titleMarathi = "मित्र मदत निधी व पुढील उपक्रम नियोजन",
                titleEnglish = "Alumni Welfare & Future Plans",
                location = "सभागृह",
                descriptionMarathi = "गरजू मित्रांना व महाविद्यालयातील गरीब विद्यार्थ्यांना शैक्षणिक मदत करण्यासाठी '१९९७ माजी विद्यार्थी कल्याण निधी' स्थापन करण्याबाबत विचारविनिमय.",
                iconName = "handshake",
                isHighlight = false,
                orderIndex = 8
            ),
            EventEntity(
                timeSlot = "०५:३० PM नंतर",
                titleMarathi = "आभार प्रदर्शन व चहापानाने सांगता",
                titleEnglish = "Vote of Thanks & High Tea",
                location = "कॅम्पस कॅन्टीन परिसर",
                descriptionMarathi = "आयोजक समितीकडून आभार प्रदर्शन, पुन्हा लवकरच भेटण्याचा संकल्प आणि सायंकाळच्या चहापानाने कार्यक्रमाची सांगता.",
                iconName = "emoji_emotions",
                isHighlight = false,
                orderIndex = 9
            )
        )
    }

    fun getCommitteeMembers(): List<CommitteeMember> {
        return listOf(
            CommitteeMember(
                name = "संजय वसंतराव कोरे",
                role = "मुख्य समन्वयक (Chief Coordinator)",
                phone = "9822451230",
                location = "उमरगा"
            ),
            CommitteeMember(
                name = "डॉ. सचिन प्रभाकर पाटील",
                role = "सह-समन्वयक व सल्लागार",
                phone = "9422034567",
                location = "सोलापूर / गुंजोटी"
            ),
            CommitteeMember(
                name = "प्रा. अमर ज्ञानोबा सूर्यवंशी",
                role = "कार्यक्रम व स्टेज व्यवस्थापन",
                phone = "9890123478",
                location = "लातूर / येणेगूर"
            ),
            CommitteeMember(
                name = "राहुल विश्वनाथ गायकवाड",
                role = "माहिती व डिजिटल संकलन प्रमुख",
                phone = "9765432109",
                location = "पुणे / उमरगा"
            ),
            CommitteeMember(
                name = "गजानन मारुती शिंदे",
                role = "भोजन व व्यवस्थापन प्रमुख",
                phone = "9860124578",
                location = "उमरगा / पुणे"
            ),
            CommitteeMember(
                name = "समाधान तुकाराम जाधव",
                role = "स्वागत व संपर्क समिती",
                phone = "9763221100",
                location = "मुरुम"
            )
        )
    }

    fun getMemories(): List<CollegeMemoryItem> {
        return listOf(
            CollegeMemoryItem(
                title = "कॉलेज कट्टा आणि सायकल स्टँड",
                tag = "अविस्मरणीय कट्टा",
                description = "लेक्चर संपले की कट्ट्यावर जमून तास न् तास चालणाऱ्या गप्पा, कोणाच्या तरी सायकलवरून चक्कर मारणे आणि कॉलेज प्रवेशद्वाराजवळची ती मैत्रीची मैफल आजही जशीच्या तशी डोळ्यांसमोर उभी राहते."
            ),
            CollegeMemoryItem(
                title = "वाचनालय (Library) आणि शांतता",
                tag = "ज्ञानमंदिर",
                description = "लायब्ररी कार्ड देऊन पुस्तके काढणे, परीक्षेच्या काळात नोट्स मिळवण्यासाठी चाललेली धडपड आणि लायब्ररीतील पिंपळाच्या झाडाखाली बसून केलेले रिव्हिजन!"
            ),
            CollegeMemoryItem(
                title = "वार्षिक स्नेहसंमेलन व क्रीडा सप्ताह",
                tag = "संस्कृती व क्रीडा",
                description = "१९९६ आणि १९९७ चे वार्षिक स्नेहसंमेलन! पारंपरिक वेशभूषा, नाटके, वक्तृत्व स्पर्धा, कबड्डी आणि व्हॉलीबॉलच्या सामन्यांमधील तो जल्लोष आणि आनंद."
            ),
            CollegeMemoryItem(
                title = "आदरणीय प्राध्यापकांची शिकवण",
                tag = "गुरुवंदना",
                description = "प्राचार्य आणि सर्व प्राध्यापकांनी दिलेले संस्कार, शिस्त आणि मार्गदर्शन याच्या जोरावरच आज आमची १९९७ ची बॅच समाजात आदराने उभी आहे."
            ),
            CollegeMemoryItem(
                title = "मार्च १९९७ - बारावीचा शेवटचा दिवस",
                tag = "निरोप क्षण",
                description = "हॉलतिकिटे घेणे, शेवटच्या दिवशी एकमेकांच्या वह्यांवर घेतलेल्या स्वाक्षऱ्या आणि डोळ्यांतील अश्रू लपवत दिलेला निरोप... तो क्षण आज २५ वर्षांनंतर पुन्हा जिवंत होत आहे!"
            )
        )
    }
}
