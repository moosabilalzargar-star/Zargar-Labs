package com.example.data.repository

import com.example.data.db.AppDatabase
import com.example.data.db.CropDiaryEntity
import com.example.data.db.MarketplaceListingEntity
import com.example.data.db.SavedDiagnosisEntity
import com.example.data.db.ServiceBookingEntity
import com.example.data.db.UserEntity
import com.example.data.model.AgriService
import com.example.data.model.CropDiseaseInfo
import com.example.data.model.DistrictWeather
import com.example.data.model.GovScheme
import com.example.data.model.KnowledgeArticle
import com.example.data.model.MandiRate
import kotlinx.coroutines.flow.Flow

class AgriRepository(private val database: AppDatabase) {

    // --- DB Access ---
    val allDiaryEntries: Flow<List<CropDiaryEntity>> = database.cropDiaryDao().getAllEntries()
    val totalExpenses: Flow<Double?> = database.cropDiaryDao().getTotalExpenses()
    val totalIncome: Flow<Double?> = database.cropDiaryDao().getTotalIncome()
    val allMarketplaceListings: Flow<List<MarketplaceListingEntity>> = database.marketplaceDao().getAllListings()
    val allServiceBookings: Flow<List<ServiceBookingEntity>> = database.serviceBookingDao().getAllBookings()
    val totalBookingRevenue: Flow<Double?> = database.serviceBookingDao().getTotalBookingRevenue()
    val allSavedDiagnoses: Flow<List<SavedDiagnosisEntity>> = database.savedDiagnosisDao().getAllDiagnoses()

    suspend fun insertDiaryEntry(entry: CropDiaryEntity) = database.cropDiaryDao().insertEntry(entry)
    suspend fun deleteDiaryEntry(id: Long) = database.cropDiaryDao().deleteEntry(id)

    suspend fun insertMarketplaceListing(listing: MarketplaceListingEntity) = database.marketplaceDao().insertListing(listing)
    suspend fun deleteMarketplaceListing(id: Long) = database.marketplaceDao().deleteListing(id)

    suspend fun insertServiceBooking(booking: ServiceBookingEntity) = database.serviceBookingDao().insertBooking(booking)
    suspend fun insertSavedDiagnosis(diagnosis: SavedDiagnosisEntity) = database.savedDiagnosisDao().insertDiagnosis(diagnosis)

    suspend fun getUserByEmail(email: String): UserEntity? = database.userDao().getUserByEmail(email)
    suspend fun insertUser(user: UserEntity): Long = database.userDao().insertUser(user)
    suspend fun updateUser(user: UserEntity) = database.userDao().updateUser(user)

    suspend fun getStats(): Map<String, Int> {
        return mapOf(
            "farmers" to database.userDao().getUserCount(),
            "diaryEntries" to database.cropDiaryDao().getEntryCount(),
            "listings" to database.marketplaceDao().getListingCount(),
            "bookings" to database.serviceBookingDao().getBookingCount(),
            "diagnoses" to database.savedDiagnosisDao().getDiagnosisCount()
        )
    }

    // --- Static Kashmir Agricultural Domain Data ---
    fun getDistrictsWeather(): List<DistrictWeather> {
        return listOf(
            DistrictWeather(
                districtName = "Srinagar",
                temperatureC = 23,
                condition = "Clear Sunny",
                humidityPercent = 52,
                rainProbability = 5,
                windSpeedKmh = 8,
                frostRiskLevel = "Low",
                sprayAdvisory = "Ideal spray conditions. Mild winds and zero rain forecast for 48 hours.",
                sprayStatus = "EXCELLENT"
            ),
            DistrictWeather(
                districtName = "Baramulla",
                temperatureC = 22,
                condition = "Sunny & Warm",
                humidityPercent = 49,
                rainProbability = 10,
                windSpeedKmh = 7,
                frostRiskLevel = "Low",
                sprayAdvisory = "Perfect window for high-density apple tree cover sprays and micronutrient feed.",
                sprayStatus = "EXCELLENT"
            ),
            DistrictWeather(
                districtName = "Shopian",
                temperatureC = 19,
                condition = "Partly Cloudy",
                humidityPercent = 64,
                rainProbability = 20,
                windSpeedKmh = 11,
                frostRiskLevel = "Moderate",
                sprayAdvisory = "Spray early morning before 11:00 AM. Monitor evening wind speed in upper orchards.",
                sprayStatus = "CAUTION"
            ),
            DistrictWeather(
                districtName = "Pulwama",
                temperatureC = 23,
                condition = "Clear Sky",
                humidityPercent = 50,
                rainProbability = 10,
                windSpeedKmh = 9,
                frostRiskLevel = "Low",
                sprayAdvisory = "Pampore saffron fields dry; safe for intercultural operations and orchard sprays.",
                sprayStatus = "EXCELLENT"
            ),
            DistrictWeather(
                districtName = "Anantnag",
                temperatureC = 21,
                condition = "Mostly Sunny",
                humidityPercent = 55,
                rainProbability = 15,
                windSpeedKmh = 10,
                frostRiskLevel = "Low",
                sprayAdvisory = "Dry air ensures rapid chemical absorption without wash-off risk.",
                sprayStatus = "EXCELLENT"
            ),
            DistrictWeather(
                districtName = "Kupwara",
                temperatureC = 21,
                condition = "Sunny",
                humidityPercent = 48,
                rainProbability = 5,
                windSpeedKmh = 6,
                frostRiskLevel = "Low",
                sprayAdvisory = "Favorable weather for walnut harvesting and pest control sprays.",
                sprayStatus = "EXCELLENT"
            ),
            DistrictWeather(
                districtName = "Jammu",
                temperatureC = 33,
                condition = "Warm & Humid",
                humidityPercent = 68,
                rainProbability = 30,
                windSpeedKmh = 14,
                frostRiskLevel = "None",
                sprayAdvisory = "High ambient heat. Spray only after 5:30 PM to avoid leaf scorch on paddy crops.",
                sprayStatus = "CAUTION"
            ),
            DistrictWeather(
                districtName = "Leh (Ladakh)",
                temperatureC = 15,
                condition = "Crisp & Clear",
                humidityPercent = 25,
                rainProbability = 0,
                windSpeedKmh = 16,
                frostRiskLevel = "High Alert",
                sprayAdvisory = "Cold nighttime temperatures; protect vegetable greenhouses and apricot saplings.",
                sprayStatus = "CAUTION"
            )
        )
    }

    fun getGovSchemes(): List<GovScheme> {
        return listOf(
            GovScheme(
                id = "hadp_jk",
                title = "Holistic Agriculture Development Programme (HADP J&K)",
                acronym = "HADP J&K 2026",
                department = "Agriculture Production Dept, Govt of J&K",
                subsidyBenefit = "50% to 80% Subsidy",
                briefDescription = "Flagship ₹5,013 Crore mission across J&K. Provides huge subsidies for High-Density Apple Orchards, Polyhouses, Drip & Sprinkler Irrigation, Tractor sprayers, and Pack Houses.",
                eligibility = listOf(
                    "All registered land-owning farmers in Jammu & Kashmir",
                    "Minimum 1 Kanal landholding for high-density plantation",
                    "Must have valid Aadhaar and Bank Passbook"
                ),
                documentRequirements = listOf(
                    "Revenue Extract (Fard-e-Milkiyat / Girdawari)",
                    "Aadhaar Card copy",
                    "Bank account cancelled cheque or passbook copy",
                    "Soil test report (optional but preferred)"
                ),
                helpline = "0194-2311484",
                officialPortal = "hadp.jk.gov.in"
            ),
            GovScheme(
                id = "pm_kisan",
                title = "PM Kisan Samman Nidhi Yojana",
                acronym = "PM-KISAN",
                department = "Ministry of Agriculture & Farmers Welfare",
                subsidyBenefit = "₹6,000 / Year Direct to Bank",
                briefDescription = "Guaranteed income support of ₹6,000 annually in three equal 4-monthly installments of ₹2,000 directly transferred via DBT to eligible farming households.",
                eligibility = listOf(
                    "Small and marginal farmer families with cultivable land",
                    "Aadhaar e-KYC completed on PM Kisan portal"
                ),
                documentRequirements = listOf(
                    "Land ownership record",
                    "Aadhaar Card linked to active mobile",
                    "Aadhaar-seeded bank account"
                ),
                helpline = "155261 / 1800-115-526",
                officialPortal = "pmkisan.gov.in"
            ),
            GovScheme(
                id = "pmfby_crop",
                title = "Pradhan Mantri Fasal Bima Yojana",
                acronym = "PMFBY J&K",
                department = "Dept of Horticulture & Agriculture J&K",
                subsidyBenefit = "Full Claim for Hailstorm & Frost Damage",
                briefDescription = "Comprehensive weather-based crop insurance for Apple, Saffron, Paddy, and Mango. Premium for farmers is only 1.5% to 5%, with the rest subsidized by Central & State Govt.",
                eligibility = listOf(
                    "Loanee and non-loanee farmers growing notified crops",
                    "Crop coverage must be enrolled before seasonal cut-off date"
                ),
                documentRequirements = listOf(
                    "Crop Sowing Certificate issued by Patwari / Horticulture Officer",
                    "Bank account details",
                    "Aadhaar Card"
                ),
                helpline = "1800-180-1551",
                officialPortal = "pmfby.gov.in"
            ),
            GovScheme(
                id = "saffron_mission",
                title = "National Saffron Mission (NSM)",
                acronym = "NSM Pampore",
                department = "Directorate of Agriculture Kashmir",
                subsidyBenefit = "75% Subsidy on Saffron Rejuvenation",
                briefDescription = "Revitalizing Kashmir Saffron heritage in Pulwama, Budgam, and Kishtwar. Provides disease-free high-grade corms, automated sprinkler irrigation networks, and testing at the India Saffron Park.",
                eligibility = listOf(
                    "Farmers owning saffron land on karewas in Pulwama, Budgam, Srinagar, or Kishtwar"
                ),
                documentRequirements = listOf(
                    "Land title proving traditional or new saffron karewa plot",
                    "Kisan Credit Card / Aadhaar"
                ),
                helpline = "0194-2462159",
                officialPortal = "diragrijmu.nic.in"
            ),
            GovScheme(
                id = "kcc_loan",
                title = "Kisan Credit Card (KCC) Subsidized Loan",
                acronym = "KCC 4% Interest",
                department = "All Scheduled Commercial Banks & J&K Bank",
                subsidyBenefit = "Credit up to ₹3 Lakh at just 4% interest",
                briefDescription = "Affordable institutional credit for buying orchard fertilizers, pesticides, packaging boxes, and farm machinery without mortgaging high collateral for loans under ₹1.6 Lakh.",
                eligibility = listOf(
                    "Individual or joint owner-cultivators",
                    "Tenant farmers and oral lessees with verified cultivation proof"
                ),
                documentRequirements = listOf(
                    "Application form with J&K Bank / State Bank",
                    "Land revenue record (Fard)",
                    "Identity and residence proof"
                ),
                helpline = "1800-890-2122",
                officialPortal = "jkbank.com"
            )
        )
    }

    fun getKnowledgeArticles(): List<KnowledgeArticle> {
        return listOf(
            KnowledgeArticle(
                id = "apple_spray_guide",
                crop = "Apple",
                title = "SKUAST 2026 Apple Spray Schedule & Disease Control",
                category = "Pest Management",
                season = "Spring & Summer (Pink Bud to Harvest)",
                summary = "Complete guidance for preventing Apple Scab, Alternaria blotch, and San Jose scale in both High Density (M9 rootstock) and traditional delicious orchards.",
                keyRecommendations = listOf(
                    "Silver Tip stage: Apply Tree Spray Oil (Horticulture Mineral Oil) @ 2% to smother overwintering San Jose scale eggs.",
                    "Pink Bud stage: Spray protective fungicide Mancozeb 75 WP (300g/100L) or Captan 50 WP (300g/100L) before rainfall.",
                    "Petal Fall stage: Apply systemic fungicide Difenoconazole 25 EC (30ml/100L) or Hexaconazole 5 EC (100ml/100L).",
                    "Fruit Development stage: Avoid spraying during intense daytime sunshine to prevent chemical burn."
                )
            ),
            KnowledgeArticle(
                id = "saffron_cultivation",
                crop = "Saffron (Zafran)",
                title = "Scientific Corm Management on Kashmir Karewas",
                category = "Cultivation",
                season = "August to November",
                summary = "How to maximize stigma yield (Mongra & Lacha) and prevent destructive corm rot disease in Pampore, Chadoora, and Kishtwar soils.",
                keyRecommendations = listOf(
                    "Select healthy corms weighing > 8 grams for vigorous flowering in year one.",
                    "Treat corms with Carbendazim (1g/L) + Mancozeb (2g/L) dip for 15 minutes before sowing.",
                    "Plant in raised beds (15 cm height) with 5-7 cm spacing to prevent water stagnation.",
                    "Harvest flowers early in the morning before petals fully open to retain crocin and safranal purity."
                )
            ),
            KnowledgeArticle(
                id = "walnut_grafting",
                crop = "Walnut (Doon)",
                title = "Modern Patch Budding & Anthracnose Management",
                category = "Cultivation",
                season = "Spring & Autumn",
                summary = "Techniques for transitioning wild seedling walnut trees to thin-shelled Kaghzi varieties with higher market pricing.",
                keyRecommendations = listOf(
                    "Perform patch budding in late July to mid-August when bark slips easily.",
                    "Collect scion wood only from verified elite Mother Trees free of walnut blight.",
                    "Spray Copper Oxychloride (0.3%) after leaf fall and at bud burst to suppress anthracnose fungal spores.",
                    "Proper sun drying on clean tarpaulins preserves kernel lightness and oil content."
                )
            ),
            KnowledgeArticle(
                id = "high_density_apple",
                crop = "High Density Apple",
                title = "Trellis System, Drip Fertigation & Pruning for M9/MM106",
                category = "Cultivation",
                season = "All Year Round",
                summary = "Maximizing yield up to 40-50 tonnes/hectare in year 3 with Gala, Fuji, and Red Velox high density orchards.",
                keyRecommendations = listOf(
                    "Maintain wire trellis support firmly anchored against strong valley autumn gales.",
                    "Apply fertigation with water-soluble 19:19:19 during early leaf emergence and 13:0:45 during fruit enlargement.",
                    "Prune on tall spindle training system, removing competing vertical leaders.",
                    "Install hail nets by May to protect premium export grade skins."
                )
            )
        )
    }

    fun getCommonDiseases(): List<CropDiseaseInfo> {
        return listOf(
            CropDiseaseInfo(
                id = "apple_scab",
                cropName = "Apple (Malus domestica)",
                commonName = "Apple Scab",
                scientificName = "Venturia inaequalis",
                severityLevel = "Moderate to Severe",
                visualSymptoms = listOf(
                    "Olive-green to dull brown velvety spots on leaf surface",
                    "Lesions turning black and corky over time",
                    "Scabby cracked lesions on developing fruits causing deformity",
                    "Premature yellowing and defoliation of leaves"
                ),
                culturalPrevention = listOf(
                    "Collect and destroy fallen leaf litter in autumn using 5% Urea ground spray",
                    "Prune dense canopy to ensure rapid leaf drying after rain",
                    "Avoid overhead sprinkler irrigation that keeps leaves wet"
                ),
                recommendedSprayTreatment = "Spray systemic fungicide (e.g. Difenoconazole 25% EC @ 30ml/100L water or Dodine 65 WP @ 75g/100L water) within 48-72 hours of rain.",
                skuastReferralAdvice = "If more than 20% of orchard leaves show sporulating velvety spots, immediately bring a sample to the nearest SKUAST KVK for resistance profiling.",
                confidenceBaseline = 94
            ),
            CropDiseaseInfo(
                id = "saffron_corm_rot",
                cropName = "Saffron (Crocus sativus)",
                commonName = "Saffron Corm Rot",
                scientificName = "Fusarium oxysporum / Rhizoctonia crocorum",
                severityLevel = "Severe - Quarantine Alert",
                visualSymptoms = listOf(
                    "Sunken brown to black water-soaked necrotic lesions on corm body",
                    "Premature yellowing and drying of saffron leaves (turf)",
                    "Absence of autumn flower emergence",
                    "Foul smell from decaying rotting corms beneath soil"
                ),
                culturalPrevention = listOf(
                    "Never plant corms in poorly drained waterlogged clay fields",
                    "Enforce 3-year crop rotation with legumes or mustard",
                    "Sort and destroy all bruised, damaged or spotted corms before planting"
                ),
                recommendedSprayTreatment = "Pre-planting dip of corms in Trichoderma viride biological culture (10g/L) or Carbendazim 50 WP (1g/L) for 20 minutes.",
                skuastReferralAdvice = "High risk of Karewa-wide soil contamination! Contact SKUAST Saffron Research Station Pampore immediately if patches spread.",
                confidenceBaseline = 91
            ),
            CropDiseaseInfo(
                id = "san_jose_scale",
                cropName = "Apple & Pear",
                commonName = "San Jose Scale",
                scientificName = "Quadraspidiotus perniciosus",
                severityLevel = "Moderate",
                visualSymptoms = listOf(
                    "Small circular ash-grey to brown encrustations on twigs and limbs",
                    "Bright red/purple halos surrounding scales on green bark and fruit skin",
                    "Twig dieback and overall tree vigor decline under heavy infestation"
                ),
                culturalPrevention = listOf(
                    "Prune and burn heavily encrusted branches during winter dormancy",
                    "Conserve natural predators such as ladybird beetles and chalcid wasps"
                ),
                recommendedSprayTreatment = "Dormant / Delayed Dormant spray of Horticulture Mineral Tree Spray Oil @ 2% (2 Litres per 100L water).",
                skuastReferralAdvice = "Consult KVK if dormant oil fails to control scale crawlers in early June emergence.",
                confidenceBaseline = 89
            ),
            CropDiseaseInfo(
                id = "walnut_anthracnose",
                cropName = "Walnut (Juglans regia)",
                commonName = "Walnut Anthracnose",
                scientificName = "Gnomonia leptostyla",
                severityLevel = "Moderate",
                visualSymptoms = listOf(
                    "Small circular dark brown necrotic spots on leaflets with yellow halos",
                    "Premature leaflet dropping leaving bare petioles",
                    "Sunken black lesions on green walnut husks causing dark, shriveled kernels"
                ),
                culturalPrevention = listOf(
                    "Rake and destroy fallen leaves and diseased husks before winter",
                    "Thin lower inner branches to improve light penetration"
                ),
                recommendedSprayTreatment = "Apply Copper Oxychloride 50 WP (300g/100L) or Mancozeb 75 WP (250g/100L) at bud break and repeat after fruit set.",
                skuastReferralAdvice = "Consult local Horticulture Extension Officer if kernel quality drops across harvest batches.",
                confidenceBaseline = 88
            ),
            CropDiseaseInfo(
                id = "powdery_mildew",
                cropName = "Apple & Peach",
                commonName = "Powdery Mildew",
                scientificName = "Podosphaera leucotricha",
                severityLevel = "Mild to Moderate",
                visualSymptoms = listOf(
                    "Silvery-white powdery fungal growth on young terminal leaves and shoots",
                    "Leaves curling upwards, becoming brittle and narrow (tenting)",
                    "Russeting on apple skin with web-like netting"
                ),
                culturalPrevention = listOf(
                    "Prune out infected silvered shoot tips during winter and spring",
                    "Ensure adequate balanced nitrogen fertilization (avoid excess urea)"
                ),
                recommendedSprayTreatment = "Spray Wettable Sulphur 80 WDG (250g/100L) or Hexaconazole 5 EC (100ml/100L) at pink bud and petal fall.",
                skuastReferralAdvice = "If russeting exceeds 15% on export cultivars, seek alternative systemic triazole recommendations.",
                confidenceBaseline = 93
            )
        )
    }

    fun getMandiRates(): List<MandiRate> {
        return listOf(
            MandiRate("Parimpora Fruit Mandi (Srinagar)", "Apple", "Red Delicious", "₹1,250", "₹1,100 - ₹1,380 / box", "UP"),
            MandiRate("Sopore Fruit Mandi (Baramulla)", "Apple", "Kulu Delicious", "₹1,320", "₹1,180 - ₹1,420 / box", "UP"),
            MandiRate("Shopian Fruit Mandi", "Apple", "Royal Delicious", "₹1,280", "₹1,120 - ₹1,350 / box", "STABLE"),
            MandiRate("Pampore Saffron Exchange", "Saffron", "GI Tagged Mongra", "₹290", "₹270 - ₹320 / gram", "UP"),
            MandiRate("Kupwara Dry Fruit Mandi", "Walnut", "Kaghzi Snow White", "₹410", "₹380 - ₹440 / kg", "STABLE"),
            MandiRate("Narwal Mandi (Jammu)", "Apple", "Grade A Delicious", "₹1,360", "₹1,220 - ₹1,480 / box", "UP"),
            MandiRate("Azadpur Mandi (New Delhi)", "Apple (Kashmir)", "Premium Delicious", "₹1,620", "₹1,450 - ₹1,800 / box", "UP")
        )
    }

    fun getAgriServices(): List<AgriService> {
        return listOf(
            AgriService(
                id = "drone_spray",
                title = "Certified Drone Orchard Spray",
                subtitle = "Precision canopy coverage in minutes",
                pricePerUnit = 150.0,
                unitLabel = "per Kanal",
                badge = "Most Popular • 60% Labor Saved",
                description = "Our DGCA-certified drone pilots fly high-precision agricultural drones with electrostatic nozzles over your orchard. Delivers micro-droplets directly beneath leaves, saving 30% chemical cost and eliminating toxic manual backpack spraying.",
                highlights = listOf(
                    "Covers 10 Kanals in under 20 minutes",
                    "30% savings on chemical spray dosage",
                    "Zero human exposure to toxic pesticides",
                    "Includes GPS spray map report for your records"
                )
            ),
            AgriService(
                id = "soil_testing",
                title = "SKUAST Certified Soil Health Test",
                subtitle = "Comprehensive N-P-K & Micronutrient Lab Report",
                pricePerUnit = 499.0,
                unitLabel = "per Sample Kit",
                badge = "Official SKUAST Lab Certified",
                description = "Order our pre-packaged soil sampling kit. Our field agent collects composite soil from your orchard or karewa beds. Tested at verified laboratory for pH, Organic Carbon, Available Nitrogen, Phosphorus, Potassium, Zinc, and Boron.",
                highlights = listOf(
                    "Official digital soil health certificate",
                    "Customized fertilizer & lime dosage advisory",
                    "Prevents wasteful over-spending on DAP and Urea",
                    "Valid for HADP and bank subsidy paperwork"
                )
            ),
            AgriService(
                id = "expert_consult",
                title = "SKUAST Agronomist 1-on-1 Priority Call",
                subtitle = "Direct video/voice consult with university scientists",
                pricePerUnit = 199.0,
                unitLabel = "per 15-min Session",
                badge = "Verified Agriculture Experts",
                description = "When facing severe orchard blight, mystery leaf drop, or complex high-density rootstock issues, speak directly with SKUAST-K scientists and experienced plant pathologists without traveling to Srinagar.",
                highlights = listOf(
                    "Personal review of your crop photos and history",
                    "Written certified spray prescription sent via app",
                    "Follow-up check after 7 days included",
                    "Available in Kashmiri, Urdu, Hindi, or English"
                )
            ),
            AgriService(
                id = "kisan_pro",
                title = "Jehlum Sense Pro Club (Seasonal)",
                subtitle = "Complete digital intelligence for commercial growers",
                pricePerUnit = 299.0,
                unitLabel = "per Season",
                badge = "Premium Farmer Tier",
                description = "Unlock automated micro-climate satellite frost alarms, unlimited AI photo diagnoses, featured marketplace badges seen by top fruit merchants in Delhi and Mumbai, and priority customer care.",
                highlights = listOf(
                    "SMS & Voice call alerts 12 hours before frost or hail",
                    "Featured Gold Badge on your marketplace produce",
                    "Unlimited Gemini AI voice & text queries",
                    "Direct phone numbers of 200+ verified apple buyers"
                )
            )
        )
    }
}
